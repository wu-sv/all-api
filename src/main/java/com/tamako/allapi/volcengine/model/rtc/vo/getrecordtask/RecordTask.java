package com.tamako.allapi.volcengine.model.rtc.vo.getrecordtask;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 录制任务信息
 *
 * @author Tamako
 * @since 2024/11/15 11:29
 */
@Data
@Accessors(chain = true)
public class RecordTask {
    /**
     * 任务开始的时间，为 Unix 时间戳，单位为毫秒
     */
    private Long startTime;
    /**
     * 任务结束的时间，为 Unix 时间戳，单位为毫秒。0 表示任务未结束
     */
    private Long endTime;
    /**
     * 任务状态。取值及含义如下：
     * <p>
     * 0: 未知异常状态
     * 1: 未开始
     * 2: 运行中
     * 3: 已结束
     * 4: 任务运行失败
     */
    private Long status;
    /**
     * 任务停止的原因。取值及含义如下：
     * <p>
     * 空：表示任务未结束
     * UnknownStopReason：未知停止原因
     * StopByAPI：用户主动通过 API 停止
     * StartTaskFailed：任务启动失败
     * IdleTimeOut：超过了最大空闲时间
     */
    private String stopReason;
    /**
     * 录制生成的文件列表。
     * 当Status=2时，仅当EnableSyncUpload = true，
     * 且FileFormatConfig.FileFormat包含HLS时，RecordFileList 返回 HLS 录制文件信息，其他情况下为空。
     */
    private List<RecordFile> recordFileList;
}
