package com.tamako.allapi.volcengine.model.rtc.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Tamako
 * @since 2024/11/14 10:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GetRoomOnlineUsersResult extends BaseResult {
    /**
     * 查询到的用户总数
     */
    private Integer totalUser;
    /**
     * 查询到的不可见用户总数。不可见用户最多返回 10000 名。
     */
    private Integer totalInvisibleUser;
    /**
     * 可见用户列表
     */
    private List<String> visibleUserList;
    /**
     * 不可见用户列表
     */
    private List<String> invisibleUserList;
    /**
     * 查询的房间是否存在。
     * <p>
     * true：存在。
     * false：不存在。
     * <p>
     * 当 RoomExists 的值为 false 时，不会返回其他字段。
     */
    private Boolean roomExists;
}
