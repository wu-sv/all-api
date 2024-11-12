package com.tamako.allapi.utils;


import java.util.Date;

/**
 * @author Tamako
 * @since 2024/11/12 15:44
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {
    public static int getTimestamp() {
        return (int) ((new Date().getTime()) / 1000);
    }
}
