package com.tamako.allapi.wechat.enumerations.uploadshop.uploadshoppinginfo;


import lombok.Getter;

/**
 * @author Tamako
 * @data 2024/8/20 16:25
 */
@Getter
public enum LogisticsTypeEnum {
    physical_logistics_distribution(1,"实体物流配送"),
    Same_city_distribution(2,"同城配送"),
    Virtual_Goods(3,"虚拟商品");

    private final Integer code;
    private final String desc;

    LogisticsTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
