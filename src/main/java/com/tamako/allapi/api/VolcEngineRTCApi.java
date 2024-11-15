package com.tamako.allapi.api;


import cn.hutool.core.date.DateTime;
import com.tamako.allapi.volcengine.enums.trc.PrivilegesEnum;
import com.tamako.allapi.volcengine.model.rtc.domian.Event;
import com.tamako.allapi.volcengine.model.rtc.dto.*;
import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.StartRecordDto;
import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.StartRecordMergeStreamDto;
import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.StartRecordSingleStreamDto;
import com.tamako.allapi.volcengine.model.rtc.vo.BaseResult;
import com.tamako.allapi.volcengine.model.rtc.vo.GetRoomOnlineUsersResult;
import com.tamako.allapi.volcengine.model.rtc.vo.LimitTokenPrivilegeResult;
import com.tamako.allapi.volcengine.model.rtc.vo.ResponseVo;
import com.tamako.allapi.volcengine.model.rtc.vo.getrecordtask.GetRecordTaskResult;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;

/**
 * 火山引擎实时音视频
 *
 * @author Tamako
 * @since 2024/11/12 14:30
 */
public interface VolcEngineRTCApi {

    /**
     * 获取进入房间的AccessToken
     *
     * @param userId     用户Id
     * @param roomId     房间Id(支持通配符“*”)
     * @param expireTime 过期时间
     * @param privileges 权限(指定权限和权限的过期时间)
     * @return AccessToken
     */
    String getAccessToken(String userId, String roomId, Date expireTime, Map<PrivilegesEnum, DateTime> privileges);

    /**
     * 封禁音视频流
     * 对特定用户进行音视频流封禁
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> banUserStream(BanUserStreamDto dto);

    /**
     * 解封音视频流
     * 恢复特定用户在房间内的音视频流发布权限
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> unbanUserStream(UnbanUserStreamDto dto);

    /**
     * 封禁房间或者用户
     * 解散房间或移除指定用户
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> banRoomUser(@NotNull BanRoomUserDto dto);

    /**
     * 更新房间或者用户封禁规则
     * 对已封禁的房间或用户进行规则调整
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> updateBanRoomUserRule(@NotNull BanRoomUserDto dto);

    /**
     * 获取实时用户列表
     * 获取指定房间内的实时用户列表
     *
     * @param roomId 房间Id
     * @return 响应结果
     */
    ResponseVo<GetRoomOnlineUsersResult> getRoomOnlineUsers(@NotNull String roomId);

    /**
     * 限制 Token 发布权限
     * 对特定用户设定发布权限的限制(一般为直播时使用)
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<LimitTokenPrivilegeResult> limitTokenPrivilege(LimitTokenPrivilegeDto dto);

    /**
     * 移出用户
     * 特定用户从房间中移出
     * 被移出的用户会收到 -1006 错误通知
     * 如果被移出用户为可见用户，房间中其他用户将收到 onUserLeave 回调
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> kickUser(@NotNull KickUserDto dto);

    /**
     * 解散房间
     * 解散指定的房间
     * 房间内所有用户会收到 -1011 错误通知
     *
     * @param roomId 房间Id
     * @return 响应结果
     */
    ResponseVo<BaseResult> dismissRoom(String roomId);

    /**
     * 开始云端录制
     * 此接口实现云端录制功能，支持单流或合流录制模式
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<String> startRecord(StartRecordDto dto);

    /**
     * 开始云端录制(单流录制)
     * 此接口实现云端录制功能，仅支持单流录制模式
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<String> startRecordSingleStream(StartRecordSingleStreamDto dto);

    /**
     * 开始云端录制(合流录制)
     * 此接口实现云端录制功能，仅支持合流录制模式
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<String> startRecordMergeStream(StartRecordMergeStreamDto dto);

    /**
     * 更新云端录制
     * 对已设定的云端录制任务进行调整
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> updateRecord(UpdateRecordDto dto);

    /**
     * 结束云端录制
     * 终止正在进行的云端录制任务
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<String> stopRecord(StopRecordDto dto);

    /**
     * 查询录制任务状态
     * 获取特定录制任务的详细信息
     * 此接口适用于查询 72 小时内启动的录制任务，且将返回最新创建的任务信息
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<GetRecordTaskResult> getRecordTask(GetRecordTaskDto dto);

    /**
     * 回调检验签名
     * RTC 会为服务端回调签名。如果你希望确定来源是否是火山以及内容是否被篡改，
     * 你可以检验回调签名；否则，你可以忽略回调签名。
     *
     * @param event 事件数据
     * @return 是否通过
     */
    Boolean checkCallBackData(Event event);
}
