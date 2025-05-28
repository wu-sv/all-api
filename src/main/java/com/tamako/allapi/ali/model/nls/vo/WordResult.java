package com.tamako.allapi.ali.model.nls.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 词信息
 *
 * @author Tamako
 * @since 2025/1/17 09:56
 */
@Data
@Accessors(chain = true)
public class WordResult {
    /**
     * 词开始时间，单位为毫秒。
     */
    @Alias("BeginTime")
    private Integer beginTime;
    /**
     * 词结束时间，单位为毫秒。
     */
    @Alias("EndTime")
    private Integer endTime;
    /**
     * 该词所属音轨ID。
     */
    @Alias("ChannelId")
    private Integer channelId;
    /**
     * 词信息。
     */
    @Alias("Word")
    private String word;
}
