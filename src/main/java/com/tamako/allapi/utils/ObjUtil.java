package com.tamako.allapi.utils;


import java.util.function.Consumer;

/**
 * 对象实用程序类
 *
 * @author Tamako
 * @since 2025/2/12 09:40
 */
public class ObjUtil extends cn.hutool.core.util.ObjUtil {
    /**
     * 如果该值不为null，则将该值设置为给定的setter
     *
     * @param setter setter
     * @param value  值
     * @param <T>    类型
     */
    public static <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (isNotNull(value)) {
            setter.accept(value);
        }
    }
}
