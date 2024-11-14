package com.tamako.allapi.volcengine.model.rtc.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tamako
 * @since 2024/11/14 10:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class LimitTokenPrivilegeResult extends BaseResult {
    private Long endTime;
}
