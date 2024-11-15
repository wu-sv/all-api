package com.tamako.allapi.configuration;


import com.tamako.allapi.api.AliOSSApi;
import com.tamako.allapi.api.AliSMSApi;
import com.tamako.allapi.api.impl.AliOSSImpl;
import com.tamako.allapi.api.impl.AliSMSImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云配置类
 *
 * @author Tamako
 * @since 2024/8/30 14:50
 */
@Configuration
@EnableConfigurationProperties(AliProperties.class)
public class AliConfiguration {
    /**
     * 阿里云短信配置
     *
     * @param aliProperties 阿里云短信配置
     * @return AliSMSApi
     */
    @Bean
    public AliSMSApi aliSmsApi(AliProperties aliProperties) {
        return new AliSMSImpl(aliProperties);
    }

    /**
     * 阿里云OSS配置
     *
     * @param aliProperties 阿里云OSS配置
     * @return AliOSSApi
     */
    @Bean
    public AliOSSApi aliOssApi(AliProperties aliProperties) {
        return new AliOSSImpl(aliProperties);
    }
}
