package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 订单信息
 *
 * @author Tamako
 */
@Data
@Builder
@AllArgsConstructor
public class Order {
    /**
     * 商户交易订单编号，商户侧的交易订单详情页向用户展示的订单编号
     * 示例值: 232457563423 字符字节限制: [1, 64]
     */
    @Alias("merchant_order_no")
    @NotNull
    private String merchantOrderNo;
    /**
     * 商品跳转链接
     */
    @Alias("order_detail_jump_link")
    @NotNull
    private OrderDetailJumpLink orderDetailJumpLink;
    /**
     * 商品列表
     */
    @Alias("item_list")
    private List<Item> itemList;


}
