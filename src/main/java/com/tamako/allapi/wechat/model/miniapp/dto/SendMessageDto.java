package com.tamako.allapi.wechat.model.miniapp.dto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tamako
 */
@Data
public class SendMessageDto {
    /**
     * 所需下发的订阅模板id
     */
    @Alias("template_id")
    @NotNull
    private String templateId;
    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转
     */
    private String page;
    /**
     * 接收者（用户）的 openid
     */
    @NotNull
    private String touser;
    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }的object
     * {@link #createData(Map)}
     */
    @NotNull
    private Map<String, Map<String, String>> data;
    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    @Alias("miniprogram_state")
    @NotNull
    private String miniprogramState;
    /**
     * 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN
     */
    @NotNull
    private String lang;


    /**
     * 该参数是为了便于数据的构造，减少套层以避免出错
     * { "key1" : any ,  "key2" : any }
     *
     * @param data 简单构建的map数据
     * @return 构造好的data数据
     */
    public static Map<String, Map<String, String>> createData(Map<String, String> data) {
        Map<String, Map<String, String>> map = new HashMap<>();
        data.forEach((k, v) -> {
            Map<String, String> value = new HashMap<>();
            value.put("value", v);
            map.put(k, value);
        });
        return map;
    }
}
