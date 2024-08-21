package com.tamako.allapi.wechat;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author Tamako
 * @data 2024/8/20 11:48
 */
public class Test {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        TestObject test = new TestObject();
        test.setNow(now);
        test.setName("test");
        JSONObject jsonObject = JSONUtil.parseObj(test).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        System.out.println(JSONUtil.toJsonStr(jsonObject));
    }

    @Data
    static class TestObject {
        ZonedDateTime now;
        String name;

    }
}
