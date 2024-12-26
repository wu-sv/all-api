package com.tamako.allapi.volcengine.constants;


/**
 * @author Tamako
 * @since 2024/11/14 10:40
 */
public class RTCUrlConstant {
    //房间管理
    /**
     * 封禁音视频流
     */
    public static final String BAN_USER_STREAM_URL = "https://rtc.volcengineapi.com?Action=BanUserStream&Version=2023-11-01";
    /**
     * 解封音视频流
     */
    public static final String UNBAN_USER_STREAM_URL = "https://rtc.volcengineapi.com?Action=UnbanUserStream&Version=2023-11-01";
    /**
     * 封禁房间或者用户
     */
    public static final String BAN_ROOM_USER_URL = "https://rtc.volcengineapi.com?Action=BanRoomUser&Version=2023-11-01";

    /**
     * 更新房间或者用户封禁规则
     */
    public static final String UPDATE_BAN_ROOM_USER_RULE_URL = "https://rtc.volcengineapi.com?Action=UpdateBanRoomUserRule&Version=2023-11-01";
    /**
     * 获取实时用户列表
     */
    public static final String GET_ROOM_ONLINE_USERS_URL = "https://rtc.volcengineapi.com?Action=GetRoomOnlineUsers&AppId={}&RoomId={}&Version=2023-11-01";
    /**
     * 限制 Token 发布权限
     */
    public static final String LIMIT_TOKEN_PRIVILEGE_URL = "https://rtc.volcengineapi.com?Action=LimitTokenPrivilege&Version=2023-11-01";
    /**
     * 移出用户
     */
    public static final String KICK_USER_URL = "https://rtc.volcengineapi.com?Action=KickUser&Version=2020-12-01";
    /**
     * 解散房间
     */
    public static final String DISMISS_ROOM_URL = "https://rtc.volcengineapi.com?Action=DismissRoom&Version=2020-12-01";

    //云端录制
    /**
     * 开始云端录制
     */
    public static final String START_RECORD_URL = "https://rtc.volcengineapi.com?Action=StartRecord&Version=2023-11-01";
    /**
     * 更新云端录制
     */
    public static final String UPDATE_RECORD_URL = "https://rtc.volcengineapi.com?Action=UpdateRecord&Version=2023-11-01";
    /**
     * 结束云端录制
     */
    public static final String STOP_RECORD_URL = "https://rtc.volcengineapi.com?Action=StopRecord&Version=2023-11-01";
    /**
     * 查询录制任务状态
     */
    public static final String GET_RECORD_TASK_URL = "https://rtc.volcengineapi.com?Action=GetRecordTask&Version=2023-11-01&AppId={}&RoomId={}&TaskId={}";
}
