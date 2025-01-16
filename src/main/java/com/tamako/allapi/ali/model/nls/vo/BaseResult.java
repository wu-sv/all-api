package com.tamako.allapi.ali.model.nls.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * @author Tamako
 * @since 2025/1/16 17:25
 */
@Data
public class BaseResult {
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



}
