/**
 * 工具包
 */
package com.tamako.allapi.utils;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * 网络请求工具，封装了hutool的网络请求
 *
 * @author Tamako
 */
@Slf4j
public class NetWorkToWeChatUtil extends NetWorkUtil {


    /**
     * 同步GET请求
     *
     * @param url 请求地址
     * @return JSONObject
     */
    public static JSONObject getSync(String url) {
        return checkErrorCode(NetWorkUtil.getSync(url));
    }


    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param headers     请求头
     * @param requestBody 请求体
     * @return JSONObject
     */
    public static JSONObject postSync(@NotNull String url, Map<String, String> headers, @NotNull JSONObject requestBody) {
        return checkErrorCode(NetWorkUtil.postSync(url, headers, requestBody));
    }

    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param requestBody 请求体
     * @return byte[]
     */
    public static byte[] postSyncBytes(@NotNull String url, @NotNull JSONObject requestBody) {
        byte[] bytes = NetWorkUtil.postSyncBytes(url, requestBody);
        //判断是报错还是正常返回
        if (bytes.length <= 200) {
            JSONObject jsonObj = JSONUtil.parseObj(new String(bytes));
            checkErrorCode(jsonObj);
        }
        return bytes;
    }


    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param requestBody 请求体
     * @return JSONObject
     */
    public static JSONObject postSync(@NotNull String url, @NotNull JSONObject requestBody) {
        return postSync(url, null, requestBody);
    }

    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param requestBody 请求体
     * @return JSONObject
     */
    public static JSONObject postSync(@NotNull String url, @NotNull Map<String, String> requestBody) {
        JSONObject body = JSONUtil.parseObj(requestBody);
        return postSync(url, null, body);
    }

    /**
     * 读取请求体数据
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();

            String line;
            for (br = request.getReader(); (line = br.readLine()) != null; result.append(line)) {
                if (!result.isEmpty()) {
                    result.append("\n");
                }
            }
            line = result.toString();
            return line;
        } catch (IOException var12) {
            log.error(var12.getMessage(), var12);
            throw new AllApiException(var12);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var11) {
                    log.error("close BufferedReader error", var11);
                }
            }

        }
    }

    /**
     * 检查微信返回的错误码
     *
     * @param jsonObject JSONObject
     */
    private static JSONObject checkErrorCode(JSONObject jsonObject) {
        Integer errcode = jsonObject.getInt("errcode");
        if (errcode != null && errcode != 0) {
            String errmsg = jsonObject.getStr("errmsg");
            log.error("接口调用微信返回失败，失败状态码：{}，失败原因：{}", errcode, errmsg);
            throw new AllApiException(PlatformEnum.WX, errcode.toString(), errmsg);
        }
        return jsonObject;
    }

}
