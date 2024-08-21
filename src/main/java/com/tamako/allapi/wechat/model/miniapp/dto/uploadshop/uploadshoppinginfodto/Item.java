package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Tamako
 * @data 2024/8/20 11:19
 */
@Data
public class Item {
    /**
     * 商户侧商品ID，商户系统内部商品编码，用于标识不同的商品。请注意，当发货模式选择“分拆发货”时，需要使用商户侧商品ID来标记各物流单中包含的具体商品
     * 示例值: 1246464644 字符字节限制: [1, 64]
     */
    @Alias("merchant_item_id")
    private String merchantItemId;
    /**
     * 商品名称
     * 示例值: iPhoneX 256G 字符长度限制: [1, 256]
     */
    @NotNull
    private String name;
    /**
     * 商品描述
     * 示例值: Image形象店-深圳腾大-QQ公仔 字符长度限制: [1, 512]
     */
    private String description;
    /**
     * 商品单价（单位：分）
     * 示例值: 828800
     */
    @Alias("unit_price")
    @NotNull
    private Integer unitPrice;
    /**
     * 购买数量
     * 示例值: 2
     */
    @NotNull
    private Integer quantity;
    /**
     * 商品图片链接
     * 示例值: <a href="https://qpic.cn/xxx">...</a> 多重性: [1, 3] 字符字节限制: [1, 1024] 匹配正则表达式: ^https?😕/([^\s/?#[]@]+@)?([^\s/?#@:]+)(?::\d{2,5})?([^[]]*)$
     */
    @Alias("image_url")
    private List<String> imageUrl;

}
