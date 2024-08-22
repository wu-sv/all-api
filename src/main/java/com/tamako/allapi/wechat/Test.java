package com.tamako.allapi.wechat;


import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/20 11:48
 */
public class Test {
    public static void main(String[] args) {
        String str = JSONUtil.toJsonStr(new TestObject("123", "Tamako"));
        TestObject2 testObject2 = JSONUtil.toBean(str, TestObject2.class);
        System.out.println(JSONUtil.toJsonStr(testObject2));
    }

    @Data
    @AllArgsConstructor
    static class TestObject {
        private String now;
        private String name;
    }

    @Data
    static class TestObject2 {
        private String now;
    }
}
