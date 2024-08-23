package com.tamako.allapi.wechat.enumerations.wxpay;


/**
 * @author Tamako
 */
public enum TradeTypeEnum {
    /**
     * 公众号支付
     */
    JSAPI,
    /**
     * 扫码支付
     */
    NATIVE,
    /**
     * APP支付
     */
    App,
    /**
     * 付款码支付
     */
    MICROPAY,
    /**
     * H5支付
     */
    MWEB,
    /**
     * 刷脸支付
     */
    FACEPAY
}
