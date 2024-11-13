package com.tamako.allapi.utils;


import cn.hutool.core.util.StrUtil;

/**
 * @author Tamako
 * @since 2024/11/13 13:27
 */
public class URLUtil extends cn.hutool.core.util.URLUtil {
    /**
     * 获取url的host，不包含协议头
     *
     * @param url url
     * @return host
     */
    public static String getHostWithoutProtocol(String url) {
        url = getHost(url(url)).toString();
        if (StrUtil.isNotEmpty(url)) {
            url = url.substring(url.indexOf("://") + 3);
        }
        return url;
    }

    /**
     * 获取url的query并编码
     */
    public static String getQuery(String url) {
        return url.substring(url.indexOf("?") + 1);
    }
}
