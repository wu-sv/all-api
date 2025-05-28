/**
 * 微信模型包
 */
package com.tamako.allapi.configuration.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 微信配置类
 *
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
    @NestedConfigurationProperty
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
         * 微信支付商户密钥（APIv3秘钥）
         * 32位字符串
         * @see <a href="https://pay.weixin.qq.com/doc/v3/merchant/4013053267">什么是APIv3密钥？如何获取APIv3密钥</a>
         */
        private String mchKey;
        /**
         * 微信支付回调地址
         */
        private String notifyUrl;
        /**
         * 证书地址(需要填写绝对路径)
         * @see <a href="https://pay.weixin.qq.com/doc/v3/merchant/4013053053">什么是商户API证书？如何获取商户API证书和私钥？</a>
         */
        private String certPath;
        /**
         * 证书秘钥地址(需要填写绝对路径)
         * @see <a href="https://pay.weixin.qq.com/doc/v3/merchant/4013053053">什么是商户API证书？如何获取商户API证书和私钥？</a>
         */
        private String certKeyPath;
    }
}
