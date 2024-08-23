package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import com.tamako.allapi.wechat.enumerations.miniapp.uploadshop.uploadshoppinginfo.LogisticsTypeEnum;
import lombok.Data;

import java.util.List;


/**
 * @author Tamako
 */
@Data
public class SimpleUploadShoppingInfoDto {
    /**
     * 原支付交易对应的微信订单号
     */
    private String transactionId;
    /**
     * 购物详情列表
     */
    private List<Order> orderList;

    /**
     * 用户标识，用户在商户appid下的唯一标识
     */
    private String openId;

    /**
     * 物流形式，订单商品配送的物流形式
     *
     * @see LogisticsTypeEnum
     */
    private Integer logisticsType;

}
