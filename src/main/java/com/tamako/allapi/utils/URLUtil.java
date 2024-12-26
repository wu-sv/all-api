package com.tamako.allapi.utils;


import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

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
     *
     * @param url url
     * @return query
     */
    public static String getQuery(String url) {
        return url.substring(url.indexOf("?") + 1);
    }

    /**
     * 获取url的query参数
     *
     * @param url url
     * @return query参数
     */
    public static Map<String, String> getParams(String url) {
        Map<String, String> params = new HashMap<>();
        String query = getQuery(url);
        String[] split = query.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            params.put(split1[0], split1[1]);
        }
        return params;
    }

    /**
     * 获取url的Action参数
     *
     * @param url url
     * @return Action参数
     */
    public static String getAction(String url) {
        return getParams(url).get("Action");

    }

    /**
     * 获取url的Version参数
     *
     * @param url url
     * @return Version参数
     */
    public static String getVersion(String url) {
        return getParams(url).get("Version");
    }
}
