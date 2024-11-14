package com.tamako.allapi.volcengine.model.rtc.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Tamako
 * @since 2024/11/14 10:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetRoomOnlineUsersResult extends BaseResult{
    private Integer totalUser;
    private Integer totalInvisibleUser;
    private List<String> visibleUserList;
    private List<String> invisibleUserList;
    private Boolean roomExists;
}
