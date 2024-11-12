package com.tamako.allapi.configuration;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Tamako
 * @since 2024/8/30 14:44
 */
@Configuration
@EnableConfigurationProperties({
        WechatProperties.class,
        AliProperties.class,
        VolcEngineProperties.class
})
@Import({
        WechatConfiguration.class,
        AliConfiguration.class,
        VolcEngineConfiguration.class
})
public class APIConfiguration {
}
