package com.tamako.allapi.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台的枚举类
 *
 * @author Tamako
 * @since 2024/10/8 09:42
 */
@Getter
@AllArgsConstructor
public enum PlatformEnum {
    /**
     * 微信
     */
    WX("微信"),
    /**
     * 阿里云
     */
    ALI("阿里云"),
    /**
     * 火山引擎
     */
    VOLC_ENGINE("火山引擎");
    /**
     * 平台名称
     */
    private final String name;

}
