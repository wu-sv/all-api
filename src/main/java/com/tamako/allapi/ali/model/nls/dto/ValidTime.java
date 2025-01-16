package com.tamako.allapi.ali.model.nls.dto;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 有效时间段信息
 *
 * @author Tamako
 * @since 2025/1/16 16:53
 */
@Data
@Accessors(chain = true)
public class ValidTime {
    /**
     * 有效时间段的起始点时间偏移，单位为毫秒。
     */
    private Integer beginTime;
    /**
     * 有效时间段的结束点时间偏移，单位为毫秒。
     */
    private Integer endTime;
    /**
     * 有效时间段的作用音轨序号（从0开始）。
     */
    private Integer channelId;

}
