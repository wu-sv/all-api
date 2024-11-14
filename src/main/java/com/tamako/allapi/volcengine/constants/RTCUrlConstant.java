package com.tamako.allapi.volcengine.constants;


/**
 * @author Tamako
 * @since 2024/11/14 10:40
 */
public class RTCUrlConstant {
    //房间管理
    public static final String BAN_USER_STREAM_URL = "https://rtc.volcengineapi.com?Action=BanUserStream&Version=2023-11-01";
    public static final String UNBAN_USER_STREAM_URL = "https://rtc.volcengineapi.com?Action=UnbanUserStream&Version=2023-11-01";
    public static final String BAN_ROOM_USER_URL = "https://rtc.volcengineapi.com?Action=BanRoomUser&Version=2023-11-01";
    public static final String UPDATE_BAN_ROOM_USER_RULE_URL = "https://rtc.volcengineapi.com?Action=UpdateBanRoomUserRule&Version=2023-11-01";
    public static final String GET_ROOM_ONLINE_USERS_URL = "https://rtc.volcengineapi.com?Action=GetRoomOnlineUsers&Version=2023-11-01&AppId={}&RoomId={}";
    public static final String LIMIT_TOKEN_PRIVILEGE_URL = "https://rtc.volcengineapi.com?Action=LimitTokenPrivilege&Version=2023-11-01";
    public static final String KICK_USER_URL = "https://rtc.volcengineapi.com?Action=KickUser&Version=2020-12-01";
    public static final String DISMISS_ROOM_URL = "https://rtc.volcengineapi.com?Action=DismissRoom&Version=2020-12-01";

}
