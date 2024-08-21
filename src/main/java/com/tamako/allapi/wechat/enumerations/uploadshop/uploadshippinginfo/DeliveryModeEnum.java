package com.tamako.allapi.wechat.enumerations.uploadshop.uploadshippinginfo;


import lombok.Getter;

/**
 * @author Tamako
 * @data 2024/8/21 09:26
 */
@Getter
public enum DeliveryModeEnum {
    UNIFIED_DELIVERY(1, "统一发货"),
    SPLIT_DELIVERY(2, "分拆发货");


    private final Integer code;
    private final String desc;

    DeliveryModeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
