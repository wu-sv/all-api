package com.tamako.allapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 火山引擎配置类
 *
 * @author Tamako
 * @since 2024/11/12 19:55
 */
@Data
@ConfigurationProperties(prefix = "volc-engine")
@Component
public class VolcEngineProperties {
    /**
     * 用户秘钥id
     */
    private String accessKeyId;
    /**
     * 用户秘钥
     */
    private String secretAccessKey;
    /**
     * RTC 配置
     */
    @NestedConfigurationProperty
    private VolcEngineRTC rtc;

    @Data
    public static class VolcEngineRTC {
        /**
         * RTC 应用 ID
         */
        private String appId;
        /**
         * RTC 应用密钥
         */
        private String appKey;
    }

}
