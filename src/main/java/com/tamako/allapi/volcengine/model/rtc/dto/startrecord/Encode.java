package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 音视频编码参数
 *
 * @author Tamako
 * @since 2024/11/14 16:05
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Encode {
    /**
     * 画面宽度。取值范围为 [2, 1920]，必须是偶数，单位为像素，默认值为 640。
     * 该参数在垂直布局和并排布局下生效，自定义布局下请使用 canvas.Width 设置画面宽度。
     */
    private Integer videoWidth;
    /**
     * 画面高度，取值范围为 [2, 1920]，必须是偶数，单位为像素，默认值为 480。
     * 该参数在垂直布局和并排布局下生效，自定义布局下请使用 canvas.Height 设置画面宽度。
     */
    private Integer videoHeight;
    /**
     * 视频帧率。取值范围为 [1,60]，单位为 FPS，默认值为 15。
     */
    private Integer videoFps;
    /**
     * 视频码率。取值范围为 [0,10000]，单位为 Kbps，默认值为 0。
     * 0 表示自适应码率，会自动根据 VideoFps , VideoWidth 以及VideoHeight 计算出合理的码率。
     * 自适应码率模式下，RTC 默认不会设置超高码率。如果订阅屏幕流，建议自行设置高码率。
     * 不同场景下设置码率等视频发布参数,请参考设置视频发布参数。
     */
    private Integer videoBitrate;
    /**
     * 视频编码协议。支持取值及含义如下：
     * <p>
     * 0：H.264
     * 1：ByteVC1
     * <p>
     * 默认值为 0。
     */
    private Integer videoCodec;
    /**
     * 输出视频 GOP。取值范围为 [1,5]，单位为秒，默认值为 4。
     */
    private Integer videoGop;
    /**
     * 音频编码协议。仅支持取 0，表示 AAC 编码协议。
     */
    private Integer audioCodec;
    /**
     * 音频配置文件类型。支持取值及含义如下：
     * <p>
     * 0 :采用 LC 规格；
     * 1: 采用 HE-AAC v1 规格；
     * 2: 采用 HE-AAC v2 规格。此时，只支持输出流音频声道数为双声道。
     * <p>
     * 默认值为 0。
     */
    private Integer audioProfile;
    /**
     * 音频码率。取值范围为 [32,192],单位为 Kbps，默认值为 64。
     * <p>
     * 当AudioProfile=0时：
     * 若输入参数取值范围为 [32,192]，编码码率等于输入码率。
     * <p>
     * 当AudioProfile=1时：
     * <p>
     * 若输入参数取值范围为 [32,128]，编码码率等于输入码率。
     * 若输入参数取值范围为 [128,192]，编码码率固定为128。
     * <p>
     * 当AudioProfile=2时：
     * <p>
     * 若输入参数取值范围为 [32,64]，编码码率等于输入码率。
     * 若输入参数取值范围为 [64,192]，编码码率固定为64。
     */
    private Integer audioBitrate;
    /**
     * 音频采样率。可取值为：32000,44100，48000，单位为 Hz，默认值为 48000。
     */
    private Integer audioSampleRate;
    /**
     * 音频声道数。支持取值及含义如下：
     * <p>
     * 1：单声道。
     * 2：双声道。
     * <p>
     * 默认值为 2。
     */
    private Integer audioChannels;
}
