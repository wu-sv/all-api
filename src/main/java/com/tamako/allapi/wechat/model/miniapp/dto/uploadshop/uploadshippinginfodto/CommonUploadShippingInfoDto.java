package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.Payer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 发货信息录入dto
 *
 * @author Tamako
 * @since 2024/9/10 10:39
 */
@Data
@Builder
@AllArgsConstructor
public class CommonUploadShippingInfoDto {
    /**
     * 订单，需要上传物流信息的订单
     */
    @Alias("order_key")
    @NotNull
    private OrderKey orderKey;
    /**
     * 物流模式，发货方式枚举值：1、实体物流配送采用快递公司进行实体物流配送形式 2、同城配送 3、虚拟商品，虚拟商品，例如话费充值，点卡等，无实体配送形式 4、用户自提
     */
    @Alias("logistics_type")
    @NotNull
    private Integer logisticsType;
    /**
     * 发货模式，发货模式枚举值：1、UNIFIED_DELIVERY（统一发货）2、SPLIT_DELIVERY（分拆发货） 示例值: UNIFIED_DELIVERY
     */
    @Alias("delivery_mode")
    @NotNull
    private Integer deliveryMode;
    /**
     * 分拆发货模式时必填，用于标识分拆发货模式下是否已全部发货完成，只有全部发货完成的情况下才会向用户推送发货完成通知。示例值: true/false
     */
    @Alias("is_all_delivered")
    private Integer isAllDelivered;
    /**
     * 物流信息列表，发货物流单列表，支持统一发货（单个物流单）和分拆发货（多个物流单）两种模式，多重性: [1, 10]
     */
    @Alias("shipping_list")
    @NotNull
    private List<CommonShipping> shippingList;
    /**
     * 上传时间，用于标识请求的先后顺序 示例值: `2022-12-15T13:29:35.120+08:00`
     */
    @Alias("upload_time")
    @NotNull
    private Integer uploadTime;
    /**
     * 支付者，支付者信息
     */
    @NotNull
    private Payer payer;
}
