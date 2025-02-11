/**
 * 阿里云模型包
 */
package com.tamako.allapi.configuration.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 阿里云配置类
 *
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
    @NestedConfigurationProperty
    private AliOSS oss;

    /**
     * 阿里云 SMS 配置
     */
    @NestedConfigurationProperty
    private AliSMS sms;

    /**
     * 阿里云 FC 配置
     */
    @NestedConfigurationProperty
    private AliFC fc;

    /**
     * 阿里云 NLS 配置类
     */
    @NestedConfigurationProperty
    private AliNLS nls;

    /**
     * 阿里云 NLS 配置类
     */
    @NestedConfigurationProperty
    private AliBaiLian baiLian;

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

        /**
         * 阿里云 OSS  Bucket 所在的地域
         * 以华东1（杭州）为例，Region填写为cn-hangzhou。
         */
        private String region;
    }

    /**
     * 阿里云 SMS 配置
     */
    @Data
    public static class AliSMS {
        /**
         * 阿里云 SMS 短信签名
         */
        private String signName;
        /**
         * 阿里云 SMS 短信模板
         */
        private String templateCode;
    }

    /**
     * 阿里云 FC 配置
     */
    @Data
    public static class AliFC {
        /**
         * 阿里云 FC压缩oss文件的url
         */
        private String zipOssUrl;
    }

    /**
     * 阿里云智能语音交互配置类
     */
    @Data
    public static class AliNLS {
        /**
         * 阿里云 NLS 的应用秘钥
         */
        private String appKey;
        /**
         * 阿里云 NLS 的地域id
         * 可选参数：cn-shanghai、cn-beijing、cn-shenzhen
         */
        private String regionId;
        /**
         * 阿里云 NLS 的端点名称
         * 非必填参数
         */
        private String endpointName;
        /**
         * 阿里云 NLS 的域名
         * 非必填参数
         */
        private String domain;
    }

    /**
     * 阿里云百炼配置类
     */
    @Data
    public static class AliBaiLian {
        /**
         * 阿里云百炼的API Key
         */
        private String apiKey;
    }
}
