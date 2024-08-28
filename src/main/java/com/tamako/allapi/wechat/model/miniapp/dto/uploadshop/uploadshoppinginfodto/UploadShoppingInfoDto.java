package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshoppinginfo.LogisticsTypeEnum;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Tamako
 *
 * 上传购物详情请求参数
 */
@Data
@Builder
@AllArgsConstructor
public class UploadShoppingInfoDto {
    /**
     * 订单，需要上传购物详情的订单，根据订单类型二选一
     */
    @Alias("order_key")
    @NotNull
    private OrderKey orderKey;
    /**
     * 购物详情列表
     */
    @Alias("order_list")
    private List<Order> orderList;
    /**
     * 支付者，支付者信息
     */
    @NotNull
    private Payer payer;
    /**
     * 物流形式，订单商品配送的物流形式，默认为实体物流
     *
     * @see LogisticsTypeEnum
     */
    @Alias("logistics_type")
    private Integer logisticsType;

    /**
     * 上传时间，用于标识请求的先后顺序
     * 示例值: 2022-05-20T13:29:35.120+08:00
     */
    @Alias("upload_time")
    @NotNull
    private ZonedDateTime uploadTime;
}
