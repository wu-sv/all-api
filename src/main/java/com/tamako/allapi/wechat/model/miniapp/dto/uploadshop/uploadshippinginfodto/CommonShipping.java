package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @since 2024/9/10 10:50
 */
@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class CommonShipping {
    /**
     * 物流单号，物流快递发货时必填，示例值: 323244567777 字符字节限制: [1, 128]
     */
    @Alias("tracking_no")
    private String trackingNo;
    /**
     * 物流公司编码，快递公司ID，参见「查询物流公司编码列表」，物流快递发货时必填， 示例值: DHL 字符字节限制: [1, 128]
     */
    @Alias("express_company")
    private String expressCompany;
    /**
     * 商品信息，例如：微信红包抱枕*1个，限120个字以内
     */
    @Alias("item_desc")
    @NotNull
    private String itemDesc;
    /**
     * 联系方式，当发货的物流公司为顺丰时，联系方式为必填，收件人或寄件人联系方式二选一
     */
    private Contact contact;
}
