package com.tamako.allapi.utils;


import java.util.Date;

/**
 * @author Tamako
 * @since 2024/11/12 15:44
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {
    /**
     * 获取当前时间戳（秒）
     *
     * @return 当前时间戳（秒）
     */
    public static int getTimestampNow() {
        return getTimestamp(new Date());
    }

    /**
     * 获取指定日期的时间戳（秒）
     *
     * @param date 指定日期
     * @return 指定日期的时间戳（秒）
     */
    public static int getTimestamp(Date date) {
        return (int) ((date.getTime()) / 1000);
    }
}
