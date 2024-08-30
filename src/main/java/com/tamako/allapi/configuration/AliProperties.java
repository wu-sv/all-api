/**
 * 阿里云模型包
 */
package com.tamako.allapi.configuration;


import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 阿里云配置类
 *
 * @author Tamako
 * @since 2024/8/27 09:01
 */
@Getter
@ConfigurationProperties(prefix = "ali")
@Component
public class AliProperties {
    /**
     * 阿里云 AccessKeyId
     */
    @Value("${ali.access-key-id}")
    private String accessKeyId;
    /**
     * 阿里云 AccessKeySecret
     */
    private String accessKeySecret;
    /**
     * 阿里云 OSS 配置
     */
    @NestedConfigurationProperty
    private AliOSS oss;

    /**
     * 阿里云 SMS 配置
     */
    @NestedConfigurationProperty
    private AliSMS sms;

    /**
     * 阿里云 OSS 配置
     */
    @Getter
    public static class AliOSS {
        /**
         * 阿里云 OSS 外网访问域名
         */
        private String endpoint;
        /**
         * 阿里云 OSS 访问 Bucket 名称
         */
        private String bucketName;

        public AliOSS setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public AliOSS setBucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }
    }

    /**
     * 阿里云 SMS 配置
     */
    @Getter
    public static class AliSMS {
        /**
         * 阿里云 SMS 短信签名
         */
        private String signName;
        /**
         * 阿里云 SMS 短信模板
         */
        private String templateCode;

        public AliSMS setSignName(String signName) {
            this.signName = signName;
            return this;
        }

        public AliSMS setTemplateCode(String templateCode) {
            this.templateCode = templateCode;
            return this;
        }
    }

    public AliProperties setAccessKeySecret(final String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }


    public AliProperties setAccessKeyId(final String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }


    public AliProperties setOss(final AliOSS oss) {
        this.oss = oss;
        return this;
    }


    public AliProperties setSms(final AliSMS sms) {
        this.sms = sms;
        return this;
    }
}
