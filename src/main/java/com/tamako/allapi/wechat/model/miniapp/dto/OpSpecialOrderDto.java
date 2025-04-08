package com.tamako.allapi.wechat.model.miniapp.dto;


import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * 特殊发货参数对象
 *
 * @author Tamako
 * @since 2025/4/8 17:51
 */
@Data
@Accessors(chain = true)
public class OpSpecialOrderDto {
    /**
     * 需要特殊发货报备的订单号，可传入微信支付单号或商户单号
     */
    @NotNull
    private String orderId;
    /**
     * 特殊发货报备类型，1为预售商品订单，2为测试订单
     */
    @NotNull
    private Integer type;
    /**
     * 预计发货时间的unix时间戳，type为1时必填，type为2可省略
     */
    private Integer delayTo;
}
