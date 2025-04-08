package com.tamako.allapi.utils;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;

import java.util.Map;

/**
 * JSON工具类
 *
 * @author Tamako
 * @since 2024/11/14 09:53
 */
public class JSONUtil extends cn.hutool.json.JSONUtil {
    /**
     * 将JSON字符串转换为适合微信的JSONObject对象
     *
     * @param obj 对象
     * @return JSONObject对象
     */
    public static JSONObject parseObj2WeChat(Object obj) {
        JSONConfig jsonConfig = new JSONConfig()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .setIgnoreNullValue(true);
        return parseObj(obj, jsonConfig);
    }

    /**
     * 将JSON字符串转换为指定类型的对象，键值对的键转换为小写
     *
     * @param json      JSON字符串
     * @param beanClass 目标对象类型
     * @param <T>       目标对象类型
     * @return 目标对象
     */
    public static <T> T toBeanLowerCase(String json, Class<T> beanClass) {
        JSONObject jsonObject = JSONUtil.parseObj(json);
        return toBeanLowerCase(jsonObject, beanClass);
    }

    /**
     * 将JSON字符串转换为指定类型的对象，键值对的键转换为小写
     *
     * @param json      JSONObject
     * @param beanClass 目标对象类型
     * @param <T>       目标对象类型
     * @return 目标对象
     */
    public static <T> T toBeanLowerCase(JSONObject json, Class<T> beanClass) {
        json = toLowerKeys(json);
        return toBean(json, beanClass);
    }

    /**
     * 将JSON字符串转换为指定类型的对象，键值对的键转换为小写
     *
     * @param json          JSON字符串
     * @param typeReference 目标对象类型
     * @param <T>           目标对象类型
     * @return 目标对象
     */
    public static <T> T toBeanLowerCase(JSONObject json, TypeReference<T> typeReference) {
        JSONObject lowerKeys = toLowerKeys(json);
        return toBean(lowerKeys, typeReference, true);
    }

    /**
     * 将JSON对象中的键值对的键转换为小写
     *
     * @param json JSON对象
     * @return 转换后的JSON对象
     */
    private static JSONObject toLowerKeys(JSONObject json) {
        // 遍历所有的键值对
        JSONObject newJson = new JSONObject();
        for (Map.Entry<String, Object> stringObjectEntry : json.entrySet()) {
            // 如果值是JSONObject，递归处理
            Object lowerKeys = stringObjectEntry.getValue();
            if (stringObjectEntry.getValue() instanceof JSONObject nestedJson) {
                lowerKeys = toLowerKeys(nestedJson);
            }
            // 将键转换为小写
            String newKey = firstCharToLowerCase(stringObjectEntry.getKey());
            newJson.set(newKey, lowerKeys);
        }
        return newJson;
    }

    /**
     * 将第一个String的首字母小写
     *
     * @param str 字符串
     * @return 首字母小写的字符串
     */
    public static String firstCharToLowerCase(String str) {
        if (StrUtil.isEmpty(str)) {
            return "";
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
