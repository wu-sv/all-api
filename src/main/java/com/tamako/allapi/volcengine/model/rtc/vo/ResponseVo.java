package com.tamako.allapi.volcengine.model.rtc.vo;


import lombok.Data;

/**
 * RTC的公共响应类
 *
 * @author Tamako
 * @since 2024/11/14 09:21
 */
@Data
public class ResponseVo<T extends BaseResult> {
    private ResponseMetadata responseMetadata;

    private T result;

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

    @Data
    public static class ErrorInfo {
        private Long codeN;
        private String code;
        private String message;
    }

}
