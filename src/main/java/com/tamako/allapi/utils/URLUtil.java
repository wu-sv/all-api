package com.tamako.allapi.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;

import java.util.*;

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

    /**
     * 获取url的各级名称
     * 参考 0:com 1:aliyuncs 2:oss-cn-hangzhou
     *
     * @param url url
     * @return 各级名称
     */
    public static List<String> getLevelNames(String url) {
        String host = getHostWithoutProtocol(url);
        String[] split = host.split("\\.");
        List<String> levelNames = new ArrayList<>(Arrays.asList(split));
        CollUtil.reverse(levelNames);
        return levelNames;
    }

    /**
     * 获取ali oss url的Region
     *
     * @param url url
     * @return Region
     */
    //"https://oss-cn-hangzhou.aliyuncs.com"
    public static String getAliOssRegion(String url) {
        List<String> levelNames = getLevelNames(url);
        if (levelNames.size() < 3) {
            throw new AllApiException(PlatformEnum.ALI, "endpoint url is not valid ");
        }
        String region = levelNames.get(2);
        return region.substring(region.indexOf("-") + 1);
    }
}
