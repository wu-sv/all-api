package com.tamako.allapi.volcengine.model.rtc.vo;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * BaseResult
 *
 * @author Tamako
 * @since 2024/11/14 10:16
 */
@Data
@Accessors(chain = true)
public class BaseResult {
    /**
     * 消息
     */
    private String message;
}
