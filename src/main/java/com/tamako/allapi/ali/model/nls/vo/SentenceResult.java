package com.tamako.allapi.ali.model.nls.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * 句子识别结果
 *
 * @author Tamako
 * @since 2025/1/17 09:57
 */
@Data
public class SentenceResult {
    /**
     * 该句所属音轨ID。
     */
    @Alias("ChannelId")
    private Integer channelId;
    /**
     * 说话人Id
     */
    @Alias("SpeakerId")
    private String speakerId;
    /**
     * 该句的起始时间偏移，单位为毫秒。
     */
    @Alias("BeginTime")
    private Integer beginTime;
    /**
     * 该句的结束时间偏移，单位为毫秒。
     */
    @Alias("EndTime")
    private Integer endTime;
    /**
     * 该句的识别文本结果。
     */
    @Alias("Text")
    private String text;
    /**
     * 情绪能量值，取值为音量分贝值/10。取值范围：[1,10]。值越高情绪越强烈。
     */
    @Alias("EmotionValue")
    private Float emotionValue;
    /**
     * 本句与上一句之间的静音时长，单位为秒。
     */
    @Alias("SilenceDuration")
    private Integer silenceDuration;
    /**
     * 本句的平均语速。
     * <p>
     * 若识别语言为中文，则单位为：字数/分钟。
     * <p>
     * 若识别语言为英文，则单位为：单词数/分钟。
     */
    @Alias("SpeechRate")
    private Integer speechRate;
}
