package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshippinginfo.DeliveryModeEnum;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Tamako
 */
@Data
public class UploadShippingInfoDto {
    /**
     * 订单，需要上传物流信息的订单，需要和上传购物详情的订单类型保持一致
     */
    @Alias("order_key")
    @NotNull
    private OrderKey orderKey;
    /**
     * 发货模式，发货模式枚举值：1、UNIFIED_DELIVERY（统一发货）2、SPLIT_DELIVERY（分拆发货）
     * 示例值: 1
     * @see DeliveryModeEnum
     */
    @Alias("delivery_mode")
    @NotNull
    private Integer deliveryMode;
    /**
     * 物流信息列表，发货物流单列表，支持统一发货（单个物流单）和分拆发货（多个物流单）两种模式
     */
    @Alias("shipping_list")
    @NotNull
    private List<Shipping> shippingList;
    /**
     * 上传时间
     */
    @Alias("upload_time")
    private ZonedDateTime uploadTime;
}
