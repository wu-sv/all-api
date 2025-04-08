package com.tamako.allapi.wechat.model.miniapp.vo.getorder;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单信息
 *
 * @author Tamako
 * @since 2025/4/8 13:05
 */
@Data
@Accessors(chain = true)
public class Order {
    /**
     * 原支付交易对应的微信订单号。
     */
    @Alias("transaction_id")
    private String transactionId;
    /**
     * 支付下单商户的商户号，由微信支付生成并下发。
     */
    @Alias("merchant_id")
    private String merchantId;
    /**
     * 二级商户号。
     */
    @Alias("sub_merchant_id")
    private String subMerchantId;
    /**
     * 商户系统内部订单号，只能是数字、大小写字母`_-*`且在同一个商户号下唯一。
     */
    @Alias("merchant_trade_no")
    private String merchantTradeNo;
    /**
     * 以分号连接的该支付单的所有商品描述，当超过120字时自动截断并以 “...” 结尾。
     */
    private String description;
    /**
     * 支付单实际支付金额，整型，单位：分钱。
     */
    @Alias("paid_amount")
    private Integer paidAmount;
    /**
     * 支付者openid。
     */
    private String openid;
    /**
     * 交易创建时间，时间戳形式。
     */
    @Alias("trade_create_time")
    private Integer tradeCreateTime;
    /**
     * 支付时间，时间戳形式。
     */
    @Alias("pay_time")
    private Integer payTime;
    /**
     * 订单状态枚举：(1) 待发货；(2) 已发货；(3) 确认收货；(4) 交易完成；(5) 已退款；(6) 资金待结算。
     */
    @Alias("order_state")
    private Integer orderState;
    /**
     * 是否处在交易纠纷中。
     */
    @Alias("in_complaint")
    private Boolean inComplaint;

    /**
     * 发货信息。
     */
    @Alias("shipping")
    private ShippingInfo shipping;

}
