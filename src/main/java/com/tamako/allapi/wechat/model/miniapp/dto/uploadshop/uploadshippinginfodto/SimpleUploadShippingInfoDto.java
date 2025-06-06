package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshippinginfo.DeliveryModeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 上传商品物流信息的简单dto
 *
 * @author Tamako
 * @deprecated
 */
@Data
@Builder
@Deprecated
@AllArgsConstructor
@Accessors(chain = true)
public class SimpleUploadShippingInfoDto {
    /**
     * 原支付交易对应的微信订单号
     */
    @NotNull
    private String transactionId;
    /**
     * 发货模式，发货模式枚举值：1、UNIFIED_DELIVERY（统一发货）2、SPLIT_DELIVERY（分拆发货）
     * 示例值: 1
     *
     * @see DeliveryModeEnum
     */
    @NotNull
    private Integer deliveryMode;
    /**
     * 物流信息列表，发货物流单列表，支持统一发货（单个物流单）和分拆发货（多个物流单）两种模式
     */
    @Alias("shipping_list")
    @NotNull
    private List<Shipping> shippingList;
}
