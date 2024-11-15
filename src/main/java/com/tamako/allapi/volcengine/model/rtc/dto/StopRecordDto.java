package com.tamako.allapi.volcengine.model.rtc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 结束云端录制请求参数
 *
 * @author Tamako
 * @since 2024/11/15 11:15
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StopRecordDto {
    /**
     * 业务标识
     */
    private String businessId;
    /**
     * 房间的 ID，是房间的唯一标志
     */
    private String roomId;
    /**
     * 云端录制任务 ID。你必须对每个云端录制任务设定 TaskId，且在结束任务时也须使用该 TaskId。
     * <p>
     * TaskId 是任务的标识，在一个 AppId 的 RoomId 下 taskId 是唯一的，不同 AppId 或者不同 RoomId 下 TaskId 可以重复，
     * 因此 AppId + RoomId + TaskId 是任务的唯一标识，可以用来标识指定 AppId 下某个房间内正在运行的任务，从而能在此任务运行中进行更新或者停止此任务。
     * 关于 TaskId 及以上 Id 字段的命名规则符合正则表达式：[a-zA-Z0-9_@\-\.]{1,128}
     */
    private String taskId;
}
