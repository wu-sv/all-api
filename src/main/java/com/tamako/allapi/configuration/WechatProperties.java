/**
 * 微信模型包
 */
package com.tamako.allapi.configuration;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 微信配置类
 *
 * @author Tamako
 */
@Getter
@ConfigurationProperties(prefix = "wechat")
@Component
public class WechatProperties {
    /**
     * 微信appid
     */
    @Value("${wechat.appid}")
    private String appId;
    /**
     * 微信appSecret
     */
    private String secret;
    /**
     * 微信支付参数
     */
    @NestedConfigurationProperty
    private Pay pay;

    /**
     * 微信支付参数
     */
    @Getter
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

        public Pay setMchId(String mchId) {
            this.mchId = mchId;
            return this;
        }

        public Pay setMchKey(String mchKey) {
            this.mchKey = mchKey;
            return this;
        }

        public Pay setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
            return this;
        }

        public Pay setCertPath(String certPath) {
            this.certPath = certPath;
            return this;
        }

        public Pay setCertKeyPath(String certKeyPath) {
            this.certKeyPath = certKeyPath;
            return this;
        }

        public Pay setPlatformPath(String platformPath) {
            this.platformPath = platformPath;
            return this;
        }
    }

    public WechatProperties setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public WechatProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public WechatProperties setPay(Pay pay) {
        this.pay = pay;
        return this;
    }
}
