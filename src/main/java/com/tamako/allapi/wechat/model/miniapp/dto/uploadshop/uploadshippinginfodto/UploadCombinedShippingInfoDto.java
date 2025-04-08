package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.Payer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Tamako
 * @since 2025/4/8 11:01
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
public class UploadCombinedShippingInfoDto {
    /**
     * 订单，需要上传物流信息的订单
     */
    @Alias("order_key")
    @NotNull
    private OrderKey orderKey;
    /**
     * 子单物流详情
     */
    @Alias("sub_orders")
    @NotNull
    private List<SubOrders> subOrders;
    /**
     * 上传时间，用于标识请求的先后顺序 示例值: `2022-12-15T13:29:35.120+08:00`
     */
    @Alias("upload_time")
    @NotNull
    private String uploadTime;
    /**
     * 支付者，支付者信息
     */
    @NotNull
    private Payer payer;

}
