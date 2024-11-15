package com.tamako.allapi.volcengine.model.rtc.dto;


import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.Layout;
import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.TargetStreams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @since 2024/11/15 10:54
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecordDto {
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
    private TargetStreams targetStreams;
    /**
     * 布局参数。仅在合流模式（即 RecordMode=0）时下支持设置布局参数。
     */
    private Layout layout;
}
