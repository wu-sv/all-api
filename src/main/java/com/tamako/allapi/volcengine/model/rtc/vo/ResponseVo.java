package com.tamako.allapi.volcengine.model.rtc.vo;


import lombok.Data;

/**
 * RTC的公共响应类
 *
 * @author Tamako
 * @since 2024/11/14 09:21
 */
@Data
public class ResponseVo<T> {
    /**
     * 响应元数据
     */
    private ResponseMetadata responseMetadata;
    /**
     * 响应结果
     */
    private T result;

    /**
     * 响应元数据
     */
    @Data
    public static class ResponseMetadata {
        /**
         * 请求标识
         */
        private String requestId;
        /**
         * 接口名称
         */
        private String action;
        /**
         * 接口版本
         */
        private String version;
        /**
         * 接口所属服务
         */
        private String service;
        /**
         * 地域参数：
         * cn-north-1 (华北)
         * ap-singapore-1 (新加坡)
         * us-east-1 (美东)
         */
        private String region;
        /**
         * 仅在请求失败时返回。
         */
        private ErrorInfo error;
    }

    /**
     * 仅在请求失败时返回
     */
    @Data
    public static class ErrorInfo {
        /**
         * codeN
         */
        private Long codeN;
        /**
         * code
         */
        private String code;
        /**
         * message
         */
        private String message;
    }

}
