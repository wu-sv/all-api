package com.tamako.allapi.volcengine.model.rtc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/14 15:10
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LimitTokenPrivilegeDto {
    /**
     * 指定房间 ID
     */
    private String roomId;
    /**
     * 指定用户 ID
     */
    private String userId;
    /**
     * 需要限制发布权限的 Token
     */
    private String token;
    /**
     * 限制 Token 发布权限时长，取值范围为 [60,259200]，单位为秒。
     * 若传入值大于 0，且小于 60，自动修改为 60。
     * 若传入值大于 259200，自动修改为 259200。
     */
    private Integer forbiddenInterval;
}
