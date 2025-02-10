package com.tamako.allapi.configuration;

import com.tamako.allapi.api.VolcEngineRTCApi;
import com.tamako.allapi.api.impl.VolcEngineRTCImpl;
import com.tamako.allapi.configuration.properties.VolcEngineProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * VolcEngine RTC配置类
 *
 * @author Tamako
 * @since 2024/11/12 19:55
 */
@Configuration
@EnableConfigurationProperties(VolcEngineProperties.class)
public class VolcEngineConfiguration {
    /**
     * 创建VolcEngineRTC API的新实例
     *
     * @param properties properties
     * @return VolcEngineRTC API的新实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "volc-engine.rtc", name = "app-key")
    public VolcEngineRTCApi volcEngineRtcApi(VolcEngineProperties properties) {
        return new VolcEngineRTCImpl(properties);
    }
}
