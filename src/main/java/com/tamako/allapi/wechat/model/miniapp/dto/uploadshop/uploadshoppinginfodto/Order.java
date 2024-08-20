package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Tamako
 * @data 2024/8/20 10:53
 */
@Data
public class Order {
    /**
     * 商户交易订单编号，商户侧的交易订单详情页向用户展示的订单编号
     * 示例值: 232457563423 字符字节限制: [1, 64]
     */
    @Alias("merchant_order_no")
    @NotNull
    private String merchantOrderNo;
    @Alias("order_detail_jump_link")
    @NotNull
    private OrderDetailJumpLink orderDetailJumpLink;
    @Alias("item_list")
    private List<Item> itemList;


}
