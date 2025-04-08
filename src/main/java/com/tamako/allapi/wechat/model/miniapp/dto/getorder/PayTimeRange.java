package com.tamako.allapi.wechat.model.miniapp.dto.getorder;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付时间所属范围
 *
 * @author Tamako
 * @since 2025/4/8 17:22
 */
@Data
@Accessors(chain = true)
public class PayTimeRange {
    /**
     * 起始时间，时间戳形式，不填则视为从0开始。
     */
    @Alias("begin_time")
    private Integer beginTime;
    /**
     * 结束时间（含），时间戳形式，不填则视为32位无符号整型的最大值。
     */
    @Alias("end_time")
    private Integer endTime;
}
