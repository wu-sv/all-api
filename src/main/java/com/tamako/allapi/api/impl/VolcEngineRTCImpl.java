package com.tamako.allapi.api.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
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
import com.tamako.allapi.volcengine.model.rtc.domian.Event;
import com.tamako.allapi.volcengine.model.rtc.dto.*;
import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.*;
import com.tamako.allapi.volcengine.model.rtc.vo.BaseResult;
import com.tamako.allapi.volcengine.model.rtc.vo.GetRoomOnlineUsersResult;
import com.tamako.allapi.volcengine.model.rtc.vo.LimitTokenPrivilegeResult;
import com.tamako.allapi.volcengine.model.rtc.vo.ResponseVo;
import com.tamako.allapi.volcengine.model.rtc.vo.getrecordtask.GetRecordTaskResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     * 封禁音视频流
     * 对特定用户进行音视频流封禁
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<BaseResult> banUserStream(@NotNull BanUserStreamDto dto) {
        return super.post(RTCUrlConstant.BAN_USER_STREAM_URL, dto, properties);
    }

    /**
     * 解封音视频流
     * 恢复特定用户在房间内的音视频流发布权限
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<BaseResult> unbanUserStream(@NotNull UnbanUserStreamDto dto) {
        return super.post(RTCUrlConstant.UNBAN_USER_STREAM_URL, dto, properties);
    }

    /**
     * 封禁房间或者用户
     * 解散房间或移除指定用户
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<BaseResult> banRoomUser(@NotNull BanRoomUserDto dto) {
        return super.post(RTCUrlConstant.BAN_ROOM_USER_URL, dto, properties);
    }

    /**
     * 更新房间或者用户封禁规则
     * 对已封禁的房间或用户进行规则调整
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<BaseResult> updateBanRoomUserRule(@NotNull BanRoomUserDto dto) {
        return super.post(RTCUrlConstant.UPDATE_BAN_ROOM_USER_RULE_URL, dto, properties);
    }

    /**
     * 获取实时用户列表
     * 获取指定房间内的实时用户列表
     *
     * @param roomId 房间Id
     * @return 响应结果
     */
    @Override
    public ResponseVo<GetRoomOnlineUsersResult> getRoomOnlineUsers(@NotNull String roomId) {
        JSONObject reqBody = NetWork2VolcEngineUtil.get(StrUtil.format(RTCUrlConstant.GET_ROOM_ONLINE_USERS_URL, properties.getRtc().getAppId(), roomId), properties);
        TypeReference<ResponseVo<GetRoomOnlineUsersResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }

    /**
     * 限制 Token 发布权限
     * 对特定用户设定发布权限的限制(一般为直播时使用)
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<LimitTokenPrivilegeResult> limitTokenPrivilege(LimitTokenPrivilegeDto dto) {
        JSONObject reqBody = NetWork2VolcEngineUtil.post(RTCUrlConstant.LIMIT_TOKEN_PRIVILEGE_URL, this.addAppId(dto, properties), properties);
        TypeReference<ResponseVo<LimitTokenPrivilegeResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }

    /**
     * 移出用户
     * 特定用户从房间中移出
     * 被移出的用户会收到 -1006 错误通知
     * 如果被移出用户为可见用户，房间中其他用户将收到 onUserLeave 回调
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<BaseResult> kickUser(@NotNull KickUserDto dto) {
        return super.post(RTCUrlConstant.KICK_USER_URL, dto, properties);
    }

    /**
     * 解散房间
     * 解散指定的房间
     * 房间内所有用户会收到 -1011 错误通知
     *
     * @param roomId 房间Id
     * @return 响应结果
     */
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

    /**
     * 开始云端录制
     * 此接口实现云端录制功能，支持单流或合流录制模式
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<String> startRecord(StartRecordDto dto) {
        return super.post2String(RTCUrlConstant.START_RECORD_URL, dto, properties);
    }

    /**
     * 开始云端录制(单流录制)
     * 此接口实现云端录制功能，仅支持单流录制模式
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<String> startRecordSingleStream(StartRecordSingleStreamDto dto) {
        Encode encode = new Encode()
                .setVideoFps(dto.getVideoFps())
                .setVideoBitrate(dto.getVideoBitrate());
        Control control = new Control()
                .setMaxSilenceSeconds(dto.getMaxSilenceSeconds())
                .setMaxIdleTime(dto.getMaxIdleTime())
                .setEnableSyncUpload(dto.getEnableSyncUpload());
        FileNameConfig fileNameConfig = new FileNameConfig()
                .setPrefix(dto.getPrefix())
                .setPattern(dto.getPattern());
        StartRecordDto startRecordDto = new StartRecordDto()
                .setBusinessId(dto.getBusinessId())
                .setRoomId(dto.getRoomId())
                .setTaskId(dto.getTaskId())
                .setRecordMode(1)
                .setTargetStreams(new TargetStreams(dto.getTargetStreams()))
                .setEncode(encode)
                .setControl(control)
                .setFileFormatConfig(new FileFormatConfig(dto.getFileFormat()))
                .setFileNameConfig(fileNameConfig)
                .setStorageConfig(dto.getStorageConfig());
        return startRecord(startRecordDto);
    }

    /**
     * 开始云端录制(合流录制)
     * 此接口实现云端录制功能，仅支持合流录制模式
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<String> startRecordMergeStream(StartRecordMergeStreamDto dto) {
        Control control = new Control()
                .setMaxSilenceSeconds(dto.getMaxSilenceSeconds())
                .setMaxIdleTime(dto.getMaxIdleTime())
                .setEnableSyncUpload(dto.getEnableSyncUpload());
        FileNameConfig fileNameConfig = new FileNameConfig()
                .setPrefix(dto.getPrefix())
                .setPattern(dto.getPattern());
        StartRecordDto startRecordDto = new StartRecordDto()
                .setBusinessId(dto.getBusinessId())
                .setRoomId(dto.getRoomId())
                .setTaskId(dto.getTaskId())
                .setRecordMode(0)
                .setTargetStreams(new TargetStreams(dto.getTargetStreams()))
                .setControl(control)
                .setFileFormatConfig(new FileFormatConfig(dto.getFileFormat()))
                .setFileNameConfig(fileNameConfig)
                .setStorageConfig(dto.getStorageConfig());
        return startRecord(startRecordDto);
    }

    /**
     * 更新云端录制
     * 对已设定的云端录制任务进行调整
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<BaseResult> updateRecord(UpdateRecordDto dto) {
        return super.post(RTCUrlConstant.UPDATE_RECORD_URL, dto, properties);
    }

    /**
     * 结束云端录制
     * 终止正在进行的云端录制任务
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<String> stopRecord(StopRecordDto dto) {
        return super.post2String(RTCUrlConstant.STOP_RECORD_URL, dto, properties);
    }

    /**
     * 查询录制任务状态
     * 获取特定录制任务的详细信息
     * 此接口适用于查询 72 小时内启动的录制任务，且将返回最新创建的任务信息
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    @Override
    public ResponseVo<GetRecordTaskResult> getRecordTask(GetRecordTaskDto dto) {
        JSONObject reqBody = NetWork2VolcEngineUtil.get(
                StrUtil.format(RTCUrlConstant.GET_RECORD_TASK_URL, properties.getRtc().getAppId(), dto.getRoomId(), dto.getTaskId())
                , properties);
        TypeReference<ResponseVo<GetRecordTaskResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }

    /**
     * 回调检验签名
     * RTC 会为服务端回调签名。如果你希望确定来源是否是火山以及内容是否被篡改，
     * 你可以检验回调签名；否则，你可以忽略回调签名。
     *
     * @param event 事件数据
     * @return 是否通过
     */
    @Override
    public Boolean checkCallBackData(Event event) {
        List<String> data = new ArrayList<>();
        data.add(event.getEventType());
        data.add(JSONUtil.toJsonStr(event.getEventData()));
        data.add(event.getEventTime());
        data.add(event.getEventId());
        data.add(properties.getRtc().getAppId());
        data.add(event.getVersion());
        data.add(event.getNonce());
        data.add(properties.getRtc().getCallbackSecretKey());
        CollUtil.sort(data, null);
        String payloadData = String.join("", data);
        String signature = DigestUtil.sha256Hex(payloadData);
        System.out.println(signature);
        return event.getSignature().equals(signature);
    }
}
