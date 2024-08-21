package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/20 17:00
 */
@Data
public class Contact {
    /**
     * 寄件人联系方式，寄件人联系方式，采用掩码传输，最后4位数字不能打掩码 示例值: 189****1234, 021-****1234, ****1234, 0**2-*1234, 02-****23-10, 123-8008 值限制: 0 ≤ value ≤ 1024 字段加密: 使用APIv3定义的方式加密
     */
    @Alias("consignor_contact")
    private String consignorContact;
    /**
     * 收件人联系方式，收件人联系方式为，采用掩码传输，最后4位数字不能打掩码 示例值: 1891234, 021-****1234, ****1234, 02-*1234, 02-******23-10, ****123-8008 值限制: 0 ≤ value ≤ 1024 字段加密: 使用APIv3定义的方式加密
     */
    @Alias("receiver_contact")
    private String receiverContact;
}
