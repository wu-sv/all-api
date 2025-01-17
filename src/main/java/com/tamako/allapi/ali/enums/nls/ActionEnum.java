package com.tamako.allapi.ali.enums.nls;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 行动枚举类
 *
 * @author Tamako
 * @since 2025/1/17 13:06
 */
@Getter
@AllArgsConstructor
public enum ActionEnum {
    /**
     * 提交任务
     */
    POST_REQUEST_ACTION("SubmitTask"),
    /**
     * 获取任务结果
     */
    GET_REQUEST_ACTION("GetTaskResult");

    /**
     * 值
     */
    private final String value;

}
