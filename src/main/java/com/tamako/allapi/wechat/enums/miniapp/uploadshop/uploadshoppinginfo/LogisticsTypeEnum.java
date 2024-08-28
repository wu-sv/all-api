/**
 * 微信上传购物详情枚举包
 */
package com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshoppinginfo;


import lombok.Getter;

/**
 * @author Tamako
 *
 * 物流类型枚举
 */
@Getter
public enum LogisticsTypeEnum {
    /**
     * 实体物流配送
     */
    physical_logistics_distribution(1,"实体物流配送"),
    /**
     * 同城配送
     */
    Same_city_distribution(2,"同城配送"),
    /**
     * 虚拟商品
     */
    Virtual_Goods(3,"虚拟商品");

    /**
     * code值
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String desc;

    /**
     * 构造方法
     * @param code code值
     * @param desc 描述
     */
    LogisticsTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
