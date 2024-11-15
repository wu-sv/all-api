package com.tamako.allapi.volcengine.model.rtc.domian;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 回调时间的校验参数
 *
 * @author Tamako
 * @since 2024/11/15 13:15
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    /**
     * 回调事件类型
     */
    private String eventType;
    /**
     * 回调事件数据
     */
    private Object eventData;
    /**
     * 回调时间
     */
    private String eventTime;
    /**
     * 回调事件ID
     */
    private String eventId;
    /**
     * 回调事件版本
     */
    private String version;
    /**
     * 回调事件签名
     */
    private String nonce;
    /**
     * 回调事件加密后的数据
     */
    private String signature;
}
