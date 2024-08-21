package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Tamako
 * @data 2024/8/20 16:57
 */
@Data
public class Shipping {
    /**
     * 物流单号，示例值: 323244567777 字符字节限制: [1, 128]
     */
    @Alias("tracking_no")
    @NotNull
    private String trackingNo;
    /**
     * 物流公司编码，快递公司ID，参见「查询物流公司编码列表」 示例值: DHL 字符字节限制: [1, 128]
     */
    @Alias("express_company")
    @NotNull
    private String expressCompany;
    /**
     * 物流关联的商品列表，当统一发货（单个物流单）时，该项不填；当分拆发货（多个物流单）时，需填入各物流单关联的商品列表 多重性: [0, 50]
     */
    @Alias("item_list")
    private List<Item> itemList;
    /**
     * 联系方式，当发货的物流公司为顺丰时，联系方式为必填，收件人或寄件人联系方式二选一
     */
    private Contact contact;
}
