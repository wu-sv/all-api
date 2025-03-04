package com.tamako.allapi.volcengine.model.rtc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * BanRoomUserDto
 *
 * @author Tamako
 * @since 2024/11/14 14:38
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BanRoomUserDto {
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 封禁时长，单位：秒
     */
    private String forbiddenInterval;
}
