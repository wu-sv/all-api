package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 高级配置选项
 *
 * @author Tamako
 * @since 2024/11/14 16:22
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Control {
    /**
     * 订阅流类型。支持取值及含义如下：
     * <p>
     * 0：音视频；
     * 1：纯音频。
     * <p>
     * 默认值为0。
     */
    private Integer mediaType;
    /**
     * 补帧模式。支持取值及含义如下：
     * <p>
     * 0：补最后一帧，
     * 1：补黑帧。
     * <p>
     * 默认值为0。自动布局模式下，该参数不生效。
     * 补帧是指在音视频录制时，视频的帧率通常是固定的。但是，因为网络波动或其他原因，实际帧率可能无法达到预设的帧率。
     * 此时，需要补帧以保持视频流畅。补最后一帧意味着补充的视频帧和中断前的最后一帧相同，
     * 此时看到的画面可能会有短暂静止；补黑帧意味着补充的视频帧是全黑的。
     * <p>
     * 如果同时配置Alternateimage 和FrameInterpolationMode ，优先使用 Alternateimage参数。
     * <p>
     * 在 Region.StreamIndex 对应的视频流停止发布时, Region 对应的画布空间会根据设置填充占位图或补帧。但当视频流为屏幕流时，补帧模式不生效。
     * 当 Region.StreamIndex 对应的视频流发布后停止采集或推送时, Region 对应的画布空间会填充上一帧。
     * 当 Region.StreamIndex 对应的视频流发布时,设置的占位图或补顿模式不造成任何影响。
     */
    private Integer frameInterpolationMode;
    /**
     * 任务最大中断时间，仅对单流录制生效。取值范围为 [60, 3600],单位为秒，默认值为 3600。
     * <p>
     * 若任务中断时间小于该参数值，则根据设置的补帧模式进行补帧。
     * 若任务中断时间大于该参数值但小于空闲超时时间，任务恢复时会生成一个新文件。
     * 建议该参数取值小于 MaxIdleTime 取值。
     */
    private Integer maxSilenceSeconds;
    /**
     * 任务的空闲超时时间，超过此时间后，任务自动终止。
     * 取值范围为 [10, 86400],单位为秒，默认值为 180。
     */
    private Integer maxIdleTime;
    /**
     * 最大录制时长，取值为正整数，单位为秒。默认值为 0。0 表示不限制录制时长。
     */
    private Integer maxRecordTime;
    /**
     * 是否开启边录制边上传功能。
     * <p>
     * false：关闭
     * true：开启
     * <p>
     * 默认值为 false。
     * <p>
     * 注意
     * <p>
     * 该功能仅对HLS格式存储文件生效，即：FileFormatConfig.FileFormat取值必须包含 HLS。
     * 若 HLS 格式文件名中包含Duration填充变量，开通该功能Duration的值始终为 0。
     */
    private Boolean enableSyncUpload;
}
