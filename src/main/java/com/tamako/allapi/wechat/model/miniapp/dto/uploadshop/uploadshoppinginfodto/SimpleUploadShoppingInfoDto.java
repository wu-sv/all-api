package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshoppinginfo.LogisticsTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * @author Tamako
 */
@Data
@Builder
@AllArgsConstructor
public class SimpleUploadShoppingInfoDto {
    /**
     * 原支付交易对应的微信订单号
     */
    @NotNull
    private String transactionId;
    /**
     * 购物详情列表
     */
    @NotNull
    private List<Order> orderList;

    /**
     * 用户标识，用户在商户appid下的唯一标识
     */
    @NotNull
    private String openId;

    /**
     * 物流形式，订单商品配送的物流形式
     *
     * @see LogisticsTypeEnum
     */
    @NotNull
    private Integer logisticsType;

}
