package com.tamako.allapi.volcengine.model.rtc.domian;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import com.tamako.allapi.utils.DateUtil;
import com.tamako.allapi.utils.HmacUtil;
import com.tamako.allapi.volcengine.constants.RTCConstant;
import com.tamako.allapi.volcengine.enums.trc.PrivilegesEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * AccessToken类，用于火山引擎RTC的Token
 *
 * @author Tamako
 * @since 2024/11/12 15:34
 */
@Data
@Slf4j
public class AccessToken {
    /**
     * 应用ID
     */
    public String appId;
    /**
     * 应用密钥
     */
    public String appKey;
    /**
     * 房间ID
     */
    public String roomId;
    /**
     * 用户ID
     */
    public String userId;
    /**
     * Token签发时间
     */
    public int issuedAt;
    /**
     * Token过期时间
     */
    public int expireAt;
    /**
     * Token随机数
     */
    public int nonce;
    /**
     * Token权限
     */
    public TreeMap<Short, Integer> privileges;
    /**
     * Token签名
     */
    public byte[] signature;

    /**
     * 构造函数
     *
     * @param appId  应用ID
     * @param appKey 应用密钥
     * @param roomId 房间ID
     * @param userId 用户ID
     */
    public AccessToken(String appId, String appKey, String roomId, String userId) {
        this.appId = appId;
        this.appKey = appKey;
        this.roomId = roomId;
        this.userId = userId;
        this.issuedAt = DateUtil.getTimestampNow();
        this.nonce = RandomUtil.randomInt();
        this.privileges = new TreeMap<>();
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static String getVersion() {
        return "001";
    }

    /**
     * 添加权限
     *
     * @param privilege       权限
     * @param expireTimestamp 权限过期时间戳
     */
    public void addPrivilege(PrivilegesEnum privilege, int expireTimestamp) {
        this.privileges.put(privilege.intValue, expireTimestamp);

        if (privilege.intValue == PrivilegesEnum.PRIV_PUBLISH_STREAM.intValue) {
            this.privileges.put(PrivilegesEnum.PRIV_PUBLISH_VIDEO_STREAM.intValue, expireTimestamp);
            this.privileges.put(PrivilegesEnum.PRIV_PUBLISH_AUDIO_STREAM.intValue, expireTimestamp);
            this.privileges.put(PrivilegesEnum.PRIV_PUBLISH_DATA_STREAM.intValue, expireTimestamp);
        }
    }

    /**
     * 设置过期时间
     * ExpireTime设置令牌过期时间，默认不会过期。
     * 无论什么特权的expireTime，在expireTime之后，token都将无效。
     *
     * @param expireTimestamp 过期时间戳
     */
    public void expireTime(int expireTimestamp) {
        this.expireAt = expireTimestamp;
    }

    /**
     * 打包数据（转为byte数组）
     *
     * @return byte数组
     */
    public byte[] packMsg() {
        ByteBuf buffer = new ByteBuf();
        return buffer.put(this.nonce).put(this.issuedAt)
                .put(this.expireAt).put(this.roomId)
                .put(this.userId).putIntMap(this.privileges).asBytes();
    }


    /**
     * 序列化生成令牌字符串
     *
     * @return 令牌字符串
     */
    public String serialize() {
        byte[] msg = this.packMsg();
        try {
            this.signature = HmacUtil.hmacSign(this.appKey, msg);
        } catch (Exception e) {
            throw new AllApiException(PlatformEnum.VOLC_ENGINE, "序列化生成令牌字符串失败", e);
        }

        ByteBuf buffer = new ByteBuf();
        byte[] content = buffer.put(msg).put(signature).asBytes();

        return getVersion() + this.appId + Base64.encode(content);
    }

    /**
     * 验证Token是否有效
     *
     * @param key 应用密钥
     * @return 是否有效
     */
    public boolean verify(String key) {
        if (this.expireAt > 0 && DateUtil.getTimestampNow() > this.expireAt) {
            return false;
        }

        this.appKey = key;

        byte[] signature;

        try {
            signature = HmacUtil.hmacSign(this.appKey, this.packMsg());
        } catch (Exception e) {
            log.error("加密签名失败", e);
            return false;
        }
        return Arrays.equals(this.signature, signature);
    }

    /**
     * 解析Token字符串
     *
     * @param raw Token字符串
     * @return AccessToken对象
     */
    public static AccessToken parse(String raw) {
        AccessToken token = new AccessToken("", "", "", "");

        if (raw.length() <= RTCConstant.VERSION_LENGTH + RTCConstant.APP_ID_LENGTH) {
            return token;
        }

        if (!getVersion().equals(raw.substring(0, RTCConstant.VERSION_LENGTH))) {
            return token;
        }

        token.appId = raw.substring(RTCConstant.VERSION_LENGTH, RTCConstant.VERSION_LENGTH + RTCConstant.APP_ID_LENGTH);
        byte[] content = Base64.decode(raw.substring(RTCConstant.VERSION_LENGTH + RTCConstant.APP_ID_LENGTH));

        ByteBuf buffer = new ByteBuf(content);
        byte[] msg = buffer.readBytes();
        token.signature = buffer.readBytes();

        ByteBuf msgBuf = new ByteBuf(msg);
        token.nonce = msgBuf.readInt();
        token.issuedAt = msgBuf.readInt();
        token.expireAt = msgBuf.readInt();
        token.roomId = msgBuf.readString();
        token.userId = msgBuf.readString();
        token.privileges = msgBuf.readIntMap();

        return token;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "appId='" + appId + '\'' +
                ", appKey='" + appKey + '\'' +
                ", roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                ", issuedAt=" + issuedAt +
                ", expireAt=" + expireAt +
                ", nonce=" + nonce +
                ", privileges=" + privileges +
                ", signature=" + Arrays.toString(signature) +
                '}';
    }
}
