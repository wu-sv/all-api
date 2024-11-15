package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 云端录制合流录制参数
 *
 * @author Tamako
 * @since 2024/11/15 10:38
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StartRecordMergeStreamDto {
    /**
     * 业务标识
     */
    private String businessId;
    /**
     * 房间的 ID，是房间的唯一标志
     */
    @NotNull
    private String roomId;
    /**
     * 云端录制任务 ID。你必须对每个云端录制任务设定 TaskId，且在后续进行任务更新和结束时也须使用该 TaskId。
     * <p>
     * TaskId 是任务的标识，在一个 AppId 的 RoomId 下 taskId 是唯一的，不同 AppId 或者不同 RoomId 下 TaskId 可以重复，因此 AppId + RoomId + TaskId 是任务的唯一标识，可以用来标识指定 AppId 下某个房间内正在运行的任务，从而能在此任务运行中进行更新或者停止此任务。
     * <p>
     * 关于 TaskId 及以上 Id 字段的命名规则符合正则表达式：[a-zA-Z0-9_@\-\.]{1,128}
     * 若任务运行中，使用相同的 TaskId 重复调用开始接口不会导致请求失败，BaseResponse.Result 会提示 The task has been started. Please do not call the startup task interface repeatedly。
     */
    @NotNull
    private String taskId;
    /**
     * 需要录制的音视频流。如果参数为空，默认录制房间内所有发布的音视频流，最多 17 路流。
     * 此参数中的 stream 不得和 ExcludeStreams 中重复，若重复会报错InvalidParameter。
     */
    private List<Stream> targetStreams;
    /**
     * 任务最大中断时间，仅对单流录制生效。取值范围为 [60, 3600],单位为秒，默认值为 3600。
     * <p>
     * 若任务中断时间小于该参数值，则根据设置的补帧模式进行补帧。
     * 若任务中断时间大于该参数值但小于空闲超时时间，任务恢复时会生成一个新文件。
     * 建议该参数取值小于 MaxIdleTime 取值。
     */
    private Integer maxSilenceSeconds = 300;
    /**
     * 任务的空闲超时时间，超过此时间后，任务自动终止。
     * 取值范围为 [10, 86400],单位为秒，默认值为 180。
     */
    private Integer maxIdleTime = 3600;
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
    private Boolean enableSyncUpload = true;
    /**
     * 存储文件格式。支持取值包括：MP4、HLS、FLV、MP3、 AAC、M4A。可同时选择多个存储文件格式，最终生成所选存储格式对应的多个文件。
     * <p>
     * MP3、AAC和M4A仅在编码纯音频时有效。
     */
    private String[] fileFormat = new String[]{"HLS"};
    /**
     * 指定录制文件名的前缀，对TosConfig和CustomConfig生效。
     * 在 TOS 以及支持 S3 的第三方存储平台上，可以实现指定文件夹的功能。
     * 如：Prefix = ["directory1","directory2"]，将在录制文件名前加上前缀 "directory1/directory2/"。
     * 前缀长度最大值为 128 个字符。仅支持大小写字母、数字。
     */
    private String[] prefix;
    /**
     * 录制文件的存储平台配置。
     * <p>
     * 支持：
     * <p>
     * 火山引擎视频点播 VOD
     * 火山引擎对象存储 TOS
     * Amazon S3
     * 阿里云对象存储 OSS
     * 华为云 OBS
     * 腾讯云 COS
     * 七牛云 Kodo。
     */
    @NotNull
    private StorageConfig storageConfig;
}
