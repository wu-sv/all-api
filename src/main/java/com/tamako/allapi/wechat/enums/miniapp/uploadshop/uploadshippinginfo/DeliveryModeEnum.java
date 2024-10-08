/**
 * 微信上传物流信息枚举包
 */
package com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshippinginfo;


import lombok.Getter;

/**
 * 发货方式枚举
 *
 * @author Tamako
 */
@Getter
public enum DeliveryModeEnum {
    /**
     * 统一发货
     */
    UNIFIED_DELIVERY(1, "统一发货"),
    /**
     * 分拆发货
     */
    SPLIT_DELIVERY(2, "分拆发货");


    /**
     * code
     */
    private final Integer code;
    /**
     * desc
     */
    private final String desc;

    /**
     * 构造方法
     *
     * @param code code
     * @param desc desc
     */
    DeliveryModeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
