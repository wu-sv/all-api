/**
 * 微信支付枚举包
 */
package com.tamako.allapi.wechat.enums.wxpay;


/**
 * 交易状态枚举
 *
 * @author Tamako
 */
public enum TradeStateEnum {
    /**
     * 支付成功
     */
    SUCCESS,
    /**
     * 转入退款
     */
    REFUND,
    /**
     * 未支付
     */
    NOTPAY,
    /**
     * 已关闭
     */
    CLOSED,
    /**
     * 已撤销（付款码支付）
     */
    REVOKED,
    /**
     * 用户支付中（付款码支付）
     */
    USERPAYING,
    /**
     * 支付失败(其他原因，如银行返回失败)
     */
    PAYERROR
}
