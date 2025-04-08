package com.tamako.allapi.configuration;


import cn.hutool.core.io.FileUtil;
import com.tamako.allapi.api.WeChatPayApi;
import com.tamako.allapi.api.WechatMiniAppApi;
import com.tamako.allapi.api.impl.WeChatPayImpl;
import com.tamako.allapi.api.impl.WechatMiniAppImpl;
import com.tamako.allapi.configuration.properties.WechatProperties;
import com.tamako.allapi.exception.AllApiException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * WeChat configuration
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
    @ConditionalOnProperty(prefix = "wechat", name = "secret")
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
    @ConditionalOnProperty(prefix = "wechat.pay", name = "mch-key")
    public WeChatPayApi wechatPayApi(WechatProperties wechatProperties) {
        String platformPath = wechatProperties.getPay().getPlatformPath();
        Path path = Paths.get(platformPath);
        if (!FileUtil.exists(path, true)) {
            //创建平台证书文件
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new AllApiException(e);
            }
        }
        return new WeChatPayImpl(wechatProperties);
    }
}
