package com.tamako.allapi.api.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.api.VolcEngineRTCApi;
import com.tamako.allapi.configuration.VolcEngineProperties;
import com.tamako.allapi.utils.DateUtil;
import com.tamako.allapi.utils.JSONUtil;
import com.tamako.allapi.utils.NetWork2VolcEngineUtil;
import com.tamako.allapi.volcengine.constants.RTCUrlConstant;
import com.tamako.allapi.volcengine.enums.trc.PrivilegesEnum;
import com.tamako.allapi.volcengine.model.rtc.domian.AccessToken;
import com.tamako.allapi.volcengine.model.rtc.dto.*;
import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.StartRecordDto;
import com.tamako.allapi.volcengine.model.rtc.vo.BaseResult;
import com.tamako.allapi.volcengine.model.rtc.vo.GetRoomOnlineUsersResult;
import com.tamako.allapi.volcengine.model.rtc.vo.LimitTokenPrivilegeResult;
import com.tamako.allapi.volcengine.model.rtc.vo.ResponseVo;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;

/**
 * VolcEngine RTC API实现类
 *
 * @author Tamako
 * @since 2024/11/12 14:51
 */
public class VolcEngineRTCImpl extends VolcEngineRTCBaseImpl implements VolcEngineRTCApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();
    /**
     * 火山引擎配置
     */
    private final VolcEngineProperties properties;


    /**
     * 构造方法
     *
     * @param volcEngineProperties 火山引擎配置
     */
    public VolcEngineRTCImpl(VolcEngineProperties volcEngineProperties) {
        this.properties = volcEngineProperties;
    }

    /**
     * 获取进入房间的AccessToken
     *
     * @param userId     用户Id
     * @param roomId     房间Id(支持通配符“*”)
     * @param expireTime 过期时间
     * @param privileges 权限(指定权限和权限的过期时间)
     * @return AccessToken
     */
    @Override
    public String getAccessToken(@NotNull String userId, @NotNull String roomId, @NotNull Date expireTime, @NotNull Map<PrivilegesEnum, DateTime> privileges) {
        AccessToken accessToken = new AccessToken(
                properties.getRtc().getAppId(),
                properties.getRtc().getAppKey(),
                roomId, userId);
        accessToken.expireTime(DateUtil.getTimestamp(expireTime));
        privileges.forEach((k, v) -> accessToken.addPrivilege(k, DateUtil.getTimestamp(v)));
        return accessToken.serialize();
    }

    @Override
    public ResponseVo<BaseResult> banUserStream(@NotNull BanUserStreamDto dto) {
        return super.post(RTCUrlConstant.BAN_USER_STREAM_URL, dto, properties);
    }

    @Override
    public ResponseVo<BaseResult> unbanUserStream(@NotNull UnbanUserStreamDto dto) {
        return super.post(RTCUrlConstant.UNBAN_USER_STREAM_URL, dto, properties);
    }

    @Override
    public ResponseVo<BaseResult> banRoomUser(@NotNull BanRoomUserDto dto) {
        return super.post(RTCUrlConstant.BAN_ROOM_USER_URL, dto, properties);
    }

    @Override
    public ResponseVo<BaseResult> updateBanRoomUserRule(@NotNull BanRoomUserDto dto) {
        return super.post(RTCUrlConstant.UPDATE_BAN_ROOM_USER_RULE_URL, dto, properties);
    }

    @Override
    public ResponseVo<GetRoomOnlineUsersResult> getRoomOnlineUsers(@NotNull String roomId) {
        JSONObject reqBody = NetWork2VolcEngineUtil.get(StrUtil.format(RTCUrlConstant.GET_ROOM_ONLINE_USERS_URL, properties.getRtc().getAppId(), roomId), properties);
        TypeReference<ResponseVo<GetRoomOnlineUsersResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }

    @Override
    public ResponseVo<LimitTokenPrivilegeResult> limitTokenPrivilege(LimitTokenPrivilegeDto dto) {
        JSONObject reqBody = NetWork2VolcEngineUtil.post(RTCUrlConstant.LIMIT_TOKEN_PRIVILEGE_URL, this.addAppId(dto, properties), properties);
        TypeReference<ResponseVo<LimitTokenPrivilegeResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }

    @Override
    public ResponseVo<BaseResult> kickUser(@NotNull KickUserDto dto) {
        return super.post(RTCUrlConstant.UPDATE_BAN_ROOM_USER_RULE_URL, dto, properties);
    }

    @Override
    public ResponseVo<BaseResult> dismissRoom(@NotNull String roomId) {
        JSONObject body = new JSONObject();
        body.set("appId", properties.getRtc().getAppId());
        body.set("roomId", roomId);
        JSONObject reqBody = NetWork2VolcEngineUtil.post(RTCUrlConstant.DISMISS_ROOM_URL, body, properties);
        TypeReference<ResponseVo<BaseResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }

    public ResponseVo<BaseResult> startRecord(StartRecordDto dto) {
        return null;
    }

}
