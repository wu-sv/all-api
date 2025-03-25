package com.tamako.allapi.utils;


import com.tamako.allapi.exception.AllApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取资源文件工具类
 *
 * @author Tamako
 * @since 2025/3/25 09:16
 */
public class ResourceUtil extends cn.hutool.core.io.resource.ResourceUtil {

    /**
     * 获取properties文件
     *
     * @param fileName 文件名
     * @return properties文件
     * @throws IOException IO异常
     */
    public static Properties getProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        InputStream streamSafe = ResourceUtil.getStreamSafe(fileName);
        if (ObjUtil.isNotNull(streamSafe)) {
            properties.load(streamSafe);
        }
        return properties;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static String getVersion() {
        Properties properties;
        try {
            properties = ResourceUtil.getProperties("allapi.properties");
            return properties.getProperty("version");
        } catch (IOException e) {
            throw new AllApiException(e);
        }
    }
}
