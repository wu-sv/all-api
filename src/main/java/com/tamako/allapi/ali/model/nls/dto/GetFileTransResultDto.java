package com.tamako.allapi.ali.model.nls.dto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Tamako
 * @since 2025/1/16 16:24
 */
@Data
@Accessors(chain = true)
public class GetFileTransResultDto {
    /**
     * 存放录音文件的地址，需要在管控台中将对应项目的模型设置为支持该音频场景的模型。
     */
    @Alias("file_link")
    @NotNull
    private String fileLink;
    /**
     * 设置录音文件识别服务的版本,默认为4.0。
     */
    private String version = "4.0";

    /**
     * 是否开启返回词信息，默认为false，开启时需要设置version为4.0。
     */
    @Alias("enable_words")
    private Boolean enableWords;

    /**
     * 大于16 kHz采样率的音频是否进行自动降采样（降为16 kHz），开启时需要设置version为4.0。
     */
    @Alias("enable_sample_rate_adaptive")
    private Boolean enableSampleRateAdaptive = true;

    /**
     * 是否启用回调功能，默认值为false。
     */
    @Alias("enable_callback")
    private Boolean enableCallback;

    /**
     * 回调服务的地址，enable_callback取值为true时，本字段必选。
     * URL支持HTTP和HTTPS协议，host不可使用IP地址。
     */
    @Alias("callback_url")
    private String callbackUrl;

    /**
     * 是否开启智能分轨（开启智能分轨，即可在两方对话的语音情景下，
     * 依据每句话识别结果中的ChannelId，判断该句话的发言人为哪一方。
     * 通常先发言一方ChannelId为0，8k双声道开启分轨后默认为2个人，
     * 声道channel0和channel1就是音轨编号）
     */
    @Alias("auto_split")
    private Boolean autoSplit = true;

    /**
     * 说话人分离的确定人数方式，需要和auto_split、speaker_num这两个参数搭配使用。
     * 默认为空：8k由用户指定，16k由算法决定。
     * 1：用户指定人数，具体人数由参数speaker_num确认。
     * 2：算法决定人数。
     */
    @Alias("supervise_type")
    private Integer superviseType = 2;

    /**
     * 用于辅助指定声纹人数，取值范围为2至100的整数。8k音频默认为2，16k音频默认为100。
     * 此参数只能辅助算法尽量输出指定人数，无法保证一定会输出此人数。需要和auto_split、supervise_type这两个参数搭配使用。
     */
    @Alias("speaker_num")
    private Integer speakerNum;

    /**
     * ITN（逆文本inverse text normalization）中文数字转换阿拉伯数字。
     * 设置为True时，中文数字将转为阿拉伯数字输出
     */
    @Alias("enable_inverse_text_normalization")
    private Boolean enableInverseTextNormalization = true;

    /**
     * 过滤语气词，即声音顺滑，开启时需要设置version为4.0。
     */
    @Alias("enable_disfluency")
    private Boolean enableDisfluency = true;

    /**
     * 是否给句子加标点。默认值true（加标点）。
     */
    @Alias("enable_punctuation_prediction")
    private Boolean enablePunctuationPrediction;

    /**
     * 有效时间段信息，用来排除一些不需要的时间段。
     */
    @Alias("valid_times")
    private List<ValidTime> validTimes;

    /**
     * 允许的最大结束静音，取值范围：200~6000，默认值800，单位为毫秒。
     * 开启语义断句enable_semantic_sentence_detection后，此参数无效。
     */
    @Alias("max_end_silence")
    private Integer maxEndSilence;

    /**
     * 允许单句话最大结束时间，最小值5000，默认值60000。单位为毫秒。
     * 开启语义断句enable_semantic_sentence_detection后，此参数无效。
     */
    @Alias("max_single_segment_time")
    private Integer maxSingleSegmentTime;

    /**
     * 通过POP API创建的定制模型ID，默认不添加。
     */
    @Alias("customization_id")
    private String customizationId;

    /**
     * 创建的类热词表ID，默认不添加。
     */
    @Alias("class_vocabulary_id")
    private String classVocabularyId;

    /**
     * 创建的泛热词表ID，默认不添加。
     */
    @Alias("vocabulary_id")
    private String vocabularyId;

    /**
     * 是否启⽤语义断句，取值：true/false
     */
    @Alias("enable_semantic_sentence_detection")
    private Boolean enableSemanticSentenceDetection = true;

    /**
     * 是否启用时间戳校准功能，取值：true/false
     */
    @Alias("enable_timestamp_alignment")
    private Boolean enableTimestampAlignment = true;

    /**
     * 是否只识别首个声道，取值：true/false。（如果录音识别结果重复，您可以开启此参数。）
     * 默认为空：8k处理双声道，16k处理单声道。
     * false：8k处理双声道，16k处理双声道。
     * true：8k处理单声道，16k处理单声道。
     */
    @Alias("first_channel_only")
    private Boolean firstChannelOnly;

    /**
     * 敏感词过滤功能，支持开启或关闭，支持自定义敏感词。该参数可实现：
     * 不处理（默认，即展示原文）、过滤、替换为*。
     * 具体调用说明请见下文的自定义过滤词调用示例。
     */
    @Alias("special_word_filter")
    private String specialWordFilter;

    /**
     * 自定义标点断句。
     * 不填默认使用句号、问号、叹号断句。如果用户填写此值，则会增加使用用户指定的标点符号断句。
     * 示例：
     * 按英文逗号断句填写","
     * 按中文和英文逗号断句填写"，,"
     */
    @Alias("punctuation_mark")
    private String punctuationMark;

    /**
     * 每句最多展示字数，取值范围：[4，50]。
     * 默认为不启用该功能。启用后如不填写字数，则按照长句断句。
     * 该参数可用于字幕生成场景，控制单行字幕最大字数。
     */
    @Alias("sentence_max_length")
    private Integer sentenceMaxLength;


}
