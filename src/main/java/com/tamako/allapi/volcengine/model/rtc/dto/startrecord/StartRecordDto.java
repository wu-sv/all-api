package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/14 15:58
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartRecordDto {
    /**
     * 音视频应用的唯一标志,不用设置
     */
    private String appId;
    /**
     * 业务标识
     */
    private String businessId;
    /**
     * 房间的 ID，是房间的唯一标志
     */
    private String roomId;
    /**
     * 云端录制任务 ID。你必须对每个云端录制任务设定 TaskId，且在后续进行任务更新和结束时也须使用该 TaskId。
     * <p>
     * TaskId 是任务的标识，在一个 AppId 的 RoomId 下 taskId 是唯一的，不同 AppId 或者不同 RoomId 下 TaskId 可以重复，因此 AppId + RoomId + TaskId 是任务的唯一标识，可以用来标识指定 AppId 下某个房间内正在运行的任务，从而能在此任务运行中进行更新或者停止此任务。
     * <p>
     * 关于 TaskId 及以上 Id 字段的命名规则符合正则表达式：[a-zA-Z0-9_@\-\.]{1,128}
     * 若任务运行中，使用相同的 TaskId 重复调用开始接口不会导致请求失败，BaseResponse.Result 会提示 The task has been started. Please do not call the startup task interface repeatedly。
     */
    private String taskId;
    /**
     * 录制模式。支持取值及含义如下：
     * <p>
     * 0 ：合流录制，
     * 1 ：单流录制。
     * <p>
     * 默认值为 0。
     */
    private String recordMode;
    /**
     * 需要录制的音视频流。如果参数为空，默认录制房间内所有发布的音视频流，最多 17 路流。
     * 此参数中的 stream 不得和 ExcludeStreams 中重复，若重复会报错InvalidParameter。
     */
    private TargetStreams targetStreams;
    /**
     * 音视频流录制黑名单，即不需要录制的音视频流。
     * 黑名单仅支持配置普通音视频流，且最多可配置 17 路。此参数中的 stream 不得和 TargetStreams 中重复。
     * 默认值为空。
     */
    private ExcludeStreams excludeStreams;
    /**
     * 音视频编码参数。
     * <p>
     * 单流录制时，你仅可以设置 VideoFps 和 VideoBitrate。
     * 合流录制时，你仅可以设置 VideoWidth，VideoHeight，VideoFps，和 VideoBitrate。
     */
    private Encode encode;
    /**
     * 布局参数。仅在合流模式（即 RecordMode=0）时下支持设置布局参数。
     */
    private Layout layout;
    /**
     * 高级配置选项
     */
    private Control control;
    /**
     * 录制文件的格式设置
     */
    private FileFormatConfig fileFormatConfig;
    /**
     * 录制文件的命名设置。
     * <p>
     * 录制文件的名称由 StorageConfig.Type 和 FileNameConfig 共同决定。
     * <p>
     * 当StorageConfig.Type配置为 0 ，即存储在 TOS 平台时，录制文件名称由FilenameConfig.Prefix + FilenameConfig.Pattern +随机数组成
     * 当StorageConfig.Type配置为 1 ，即存储在 火山引擎视频点播平台 平台时，录制文件名称由FilenameConfig.Pattern + 随机数组成
     * 当StorageConfig.Type配置为 2 ，即存储在指定第三方S3 对象存储平台时，录制文件名称由FilenameConfig.Prefix + FilenameConfig.Pattern +随机数组成。
     * 例如：当 StorageConfig.Type 配置为 0 ，FilenameConfig.Prefix配置为directory1/directory2/，FilenameConfig.Pattern 配置为 {TaskId}_{RoomId}_{StartTime}_{Duration}，
     * 若此时该文件的 TaskId = mytask123456789, RoomId = myroom99991234，StartTime =1645769481126，Duration = 30s 且生成的随机八位字符为 c4694fa1，则生成录制文件的文件名为：directory1/directory2/mytask123456789_myroom99991234_1645769481126_000030_c4694fa1.mp4。
     * <p>
     * 文件名在 视频点播平台 上可以重复，但在 TOS 或第三方存储平台上默认不可以重复。
     */
    private FileNameConfig fileNameConfig;
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
    private StorageConfig storageConfig;
}
