package com.tamako.allapi.api;


import cn.hutool.core.date.DateTime;
import com.tamako.allapi.volcengine.enums.trc.PrivilegesEnum;
import com.tamako.allapi.volcengine.model.rtc.dto.*;
import com.tamako.allapi.volcengine.model.rtc.vo.BaseResult;
import com.tamako.allapi.volcengine.model.rtc.vo.GetRoomOnlineUsersResult;
import com.tamako.allapi.volcengine.model.rtc.vo.LimitTokenPrivilegeResult;
import com.tamako.allapi.volcengine.model.rtc.vo.ResponseVo;
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
     * 封禁房间&用户
     * 解散房间或移除指定用户
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> banRoomUser(@NotNull BanRoomUserDto dto);

    /**
     * 更新房间&用户封禁规则
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
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<LimitTokenPrivilegeResult> limitTokenPrivilege(LimitTokenPrivilegeDto dto);

    /**
     * 移出用户
     *
     * @param dto 请求参数
     * @return 响应结果
     */
    ResponseVo<BaseResult> kickUser(@NotNull KickUserDto dto);

    /**
     * 解散房间
     *
     * @param roomId 房间Id
     * @return 响应结果
     */
    ResponseVo<BaseResult> dismissRoom(String roomId);


}
