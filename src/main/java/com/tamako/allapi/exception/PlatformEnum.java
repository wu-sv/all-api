package com.tamako.allapi.exception;


import lombok.Getter;

/**
 * 平台的枚举类
 *
 * @author Tamako
 * @since 2024/10/8 09:42
 */
@Getter
public enum PlatformEnum {
    WX("微信"),
    ALI("阿里云");

    private final String name;

    PlatformEnum(String name) {
        this.name = name;
    }

}
