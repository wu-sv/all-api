package com.tamako.allapi.ali.model.nls.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2025/1/16 17:25
 */
@Data
@Accessors(chain = true)
public class NlsResult {
    /**
     * 识别任务ID。
     */
    @Alias("TaskId")
    private String taskid;
    /**
     * 请求ID。
     */
    @Alias("RequestId")
    private String requestId;
    /**
     * 状态码。
     */
    @Alias("StatusCode")
    private Integer statusCode;
    /**
     * 状态说明。
     */
    @Alias("StatusText")
    private String statusText;
    /**
     * 识别的音频文件总时长，单位为毫秒。
     */
    @Alias("BizDuration")
    private String bizDuration;
    /**
     * 时间戳，单位为毫秒，录音文件识别完成的时间。
     */
    @Alias("SolveTime")
    private String solveTime;
    /**
     * 识别结果对象。
     */
    @Alias("Result")
    private TransResult result;
}
