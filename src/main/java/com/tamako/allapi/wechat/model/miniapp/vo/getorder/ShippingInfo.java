package com.tamako.allapi.wechat.model.miniapp.vo.getorder;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 发货信息
 *
 * @author Tamako
 * @since 2025/4/8 14:10
 */
@Data
@Accessors(chain = true)
public class ShippingInfo {
    /**
     * 发货模式，发货模式枚举值：1、UNIFIED_DELIVERY（统一发货）2、SPLIT_DELIVERY（分拆发货） 示例值: UNIFIED_DELIVERY
     *
     * @see com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshippinginfo.DeliveryModeEnum
     */
    @Alias("delivery_mode")
    private Integer deliveryMode;
    /**
     * 物流模式，发货方式枚举值：1、实体物流配送采用快递公司进行实体物流配送形式 2、同城配送 3、虚拟商品，虚拟商品，例如话费充值，点卡等，无实体配送形式 4、用户自提
     *
     * @see com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshoppinginfo.LogisticsTypeEnum
     */
    @Alias("logistics_type")
    private Integer logisticsType;
    /**
     * 是否已完成全部发货。
     */
    @Alias("finish_shipping")
    private Boolean finishShipping;
    /**
     * 在小程序后台发货信息录入页录入的商品描述。
     */
    @Alias("goods_desc")
    private String goodsDesc;
    /**
     * 已完成全部发货的次数，未完成时为 0，完成时为 1，重新发货并完成后为 2。
     */
    @Alias("finish_shipping_count")
    private Integer finishShippingCount;
    /**
     * 物流信息列表，发货物流单列表，支持统一发货（单个物流单）和分拆发货（多个物流单）两种模式。
     */
    @Alias("shipping_list")
    private List<Shipping> shippingList;
}
