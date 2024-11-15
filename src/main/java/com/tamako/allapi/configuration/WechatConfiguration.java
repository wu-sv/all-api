package com.tamako.allapi.configuration;


import com.tamako.allapi.api.WeChatPayApi;
import com.tamako.allapi.api.WechatMiniAppApi;
import com.tamako.allapi.api.impl.WeChatPayImpl;
import com.tamako.allapi.api.impl.WechatMiniAppImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wechat configuration
 *
 * @author Tamako
 * @since 2024/8/30 14:47
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WechatProperties.class)
public class WechatConfiguration {
    /**
     * Wechat Mini App API
     *
     * @param wechatProperties wechatProperties
     * @return WechatMiniAppApi
     */
    @Bean
    public WechatMiniAppApi wechatMiniAppApi(WechatProperties wechatProperties) {
        return new WechatMiniAppImpl(wechatProperties);
    }

    /**
     * WeChat Pay API
     *
     * @param wechatProperties wechatProperties
     * @return WeChatPayApi
     */
    @Bean
    public WeChatPayApi wechatPayApi(WechatProperties wechatProperties) {
        return new WeChatPayImpl(wechatProperties);
    }
}
