package com.tamako.allapi.wechat.model;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Tamako
 */
@Data
@ConfigurationProperties(prefix = "wechat")
@Component
public class WechatProperties {
    /**
     * 微信appid
     */
    private String appId;
    /**
     * 微信appSecret
     */
    private String secret;
    /**
     * 微信支付参数
     */
    private Pay pay;

    /**
     * 微信支付参数
     */
    @Data
    public static class Pay {
        /**
         * 微信支付商户号
         */
        private String mchId;
        /**
         * 微信支付商户密钥
         */
        private String mchKey;
        /**
         * 微信支付回调地址
         */
        private String notifyUrl;
        /**
         * 证书地址
         */
        private String certPath;
        /**
         * 证书秘钥地址
         */
        private String certKeyPath;
        /**
         * 微信支付平台证书路径
         */
        private String platformPath;
    }
}
