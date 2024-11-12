package com.tamako.allapi.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tamako
 * @since 2024/11/12 19:55
 */
@Configuration
@EnableConfigurationProperties(VolcEngineProperties.class)
public class VolcEngineConfiguration {
}
