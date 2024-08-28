package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.enums.miniapp.uploadshop.OrderNumberTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tamako
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderKey {
    /**
     * 订单单号类型，用于确认需要上传详情的订单
     * @see OrderNumberTypeEnum
     */
    @Alias("order_number_type")
    private Integer orderNumberType;
    /**
     * 原支付交易对应的微信订单号
     */
    @Alias("transaction_id")
    private String transactionId;
    /**
     * 支付下单商户的商户号，由微信支付生成并下发。
     */
    private String mchid;
    /**
     * 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     */
    @Alias("out_trade_no")
    private String outTradeNo;
}
