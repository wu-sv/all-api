package com.tamako.allapi.volcengine.model.rtc.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/14 10:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LimitTokenPrivilegeResult extends BaseResult {
    /**
     * 请求成功时返回 “Success"，失败时为空
     */
    private String message;
    /**
     * Token 权限限制结束时间，Unix 时间，单位为秒
     */
    private Long endTime;
}
