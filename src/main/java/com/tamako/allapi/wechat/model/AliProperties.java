package com.tamako.allapi.wechat.model;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Tamako
 * @since 2024/8/27 09:01
 */
@Data
@ConfigurationProperties(prefix = "ali")
@Component
public class AliProperties {
    /**
     * 阿里云 AccessKeyId
     */
    private String accessKeyId;
    /**
     * 阿里云 AccessKeySecret
     */
    private String accessKeySecret;
    /**
     * 阿里云 OSS 配置
     */
    private AliOSS oss;


    /**
     * 阿里云 OSS 配置
     */
    @Data
    public static class AliOSS {
        /**
         * 阿里云 OSS 外网访问域名
         */
        private String endpoint;
        /**
         * 阿里云 OSS 访问 Bucket 名称
         */
        private String bucketName;
    }
}
