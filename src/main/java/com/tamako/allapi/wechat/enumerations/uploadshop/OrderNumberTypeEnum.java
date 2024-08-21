package com.tamako.allapi.wechat.enumerations.uploadshop;


import lombok.Getter;

/**
 * @author Tamako
 * @data 2024/8/20 14:35
 */
@Getter
public enum OrderNumberTypeEnum {
    MERCHANT_SIDE_NUMBER(1, "通过下单商户号和商户侧单号确定一笔订单"),
    WECHAT_PAYMENT_NUMBER(2, "通过微信支付订单号确定一笔订单");

    private final Integer code;
    private final String desc;

    OrderNumberTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
