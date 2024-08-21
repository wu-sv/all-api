package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/20 16:59
 */
@Data
public class Item {
    /**
     * 商户侧商品ID，商户系统内部商品编码，分拆发货模式下为必填，用于标识每笔物流单号内包含的商品，需与「上传购物详情」中传入的商品ID匹配
     * 示例值: 1246464644 字符字节限制: [1, 64]
     */
    @Alias("merchant_item_id")
    private String merchantItemId;
}
