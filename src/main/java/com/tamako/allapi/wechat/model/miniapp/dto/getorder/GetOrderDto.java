package com.tamako.allapi.wechat.model.miniapp.dto.getorder;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取订单详情请求参数
 *
 * @author Tamako
 * @since 2025/4/8 16:49
 */
@Data
@Accessors(chain = true)
public class GetOrderDto {
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
     * 商户系统内部订单号，只能是数字、大小写字母`_-*`且在同一个商户号下唯一
     */
    @Alias("merchant_trade_no")
    private String merchantTradeNo;
}
