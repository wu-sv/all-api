/**
 * 微信购物订单枚举包
 */
package com.tamako.allapi.wechat.enums.miniapp.uploadshop;


import lombok.Getter;

/**
 * 订单号类型枚举
 *
 * @author Tamako
 */
@Getter
public enum OrderNumberTypeEnum {
    /**
     * 通过下单商户号和商户侧单号确定一笔订单
     */
    MERCHANT_SIDE_NUMBER(1, "通过下单商户号和商户侧单号确定一笔订单"),
    /**
     * 通过微信支付订单号确定一笔订单
     */
    WECHAT_PAYMENT_NUMBER(2, "通过微信支付订单号确定一笔订单");

    /**
     * code值
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String desc;

    /**
     * 构造方法
     *
     * @param code code值
     * @param desc 描述
     */
    OrderNumberTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
