package com.tamako.allapi.volcengine.model.rtc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/14 15:23
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class KickUserDto {
    /**
     * 房间号
     */
    private String roomId;
    /**
     * 用户ID
     */
    private String userId;
}
