package com.tamako.allapi.wechat.model.miniapp;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Tamako
 * @data 2024/8/21 13:46
 */
@Data
@ConfigurationProperties("wechat")
@Component
public class WechatProperties {
    private String appId;
    private String secret;
}
