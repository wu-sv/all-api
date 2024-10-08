/**
 * 工具包
 */
package com.tamako.allapi.utils;


import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
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

import static cn.hutool.http.HttpRequest.get;
import static cn.hutool.http.HttpRequest.post;

/**
 * 网络请求工具，封装了hutool的网络请求
 *
 * @author Tamako
 */
@Slf4j
public class NetWorkUtil {


    /**
     * 同步GET请求
     *
     * @param url 请求地址
     * @return JSONObject
     */
    public static JSONObject getSync(String url) {
        HttpRequest request = get(url);
        return handleResponse(request);
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
        HttpRequest request = post(url)
                .addHeaders(headers)
                .body(JSONUtil.toJsonStr(requestBody), ContentType.JSON.getValue());
        return handleResponse(request);
    }

    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param requestBody 请求体
     * @return byte[]
     */
    public static byte[] postSyncBytes(@NotNull String url, @NotNull JSONObject requestBody) {
        HttpRequest request = post(url)
                .body(JSONUtil.toJsonStr(requestBody), ContentType.JSON.getValue());
        return handleBytesResponse(request);
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
     * 处理响应数据
     *
     * @param request Request
     * @return JSONObject
     */
    private static JSONObject handleResponse(HttpRequest request) {
        try (HttpResponse response = request.execute()) {
            String body = response.body();
            if (response.body() == null) {
                log.error("response body is null,please check your request");
                throw new AllApiException("response body is null");
            }
            JSONObject obj = JSONUtil.parseObj(body);
            //处理微信的错误码
            checkErrorCode(obj);
            return obj;
        } catch (AllApiException e) {
            throw e;
        } catch (Exception e) {
            throw new AllApiException(e);
        }
    }


    /**
     * 处理字节响应数据
     *
     * @param request Request
     * @return byte[]
     */
    private static byte[] handleBytesResponse(HttpRequest request) {
        try (HttpResponse response = request.execute()) {
            byte[] bytes = response.bodyBytes();
            if (bytes == null) {
                log.error("response body is null,pleas check your request");
                throw new AllApiException("response body is null");
            }
            //判断是报错还是正常返回
            if (bytes.length <= 200) {
                JSONObject jsonObj = JSONUtil.parseObj(new String(bytes));
                checkErrorCode(jsonObj);
            }
            return bytes;
        } catch (AllApiException e) {
            throw e;
        } catch (Exception e) {
            throw new AllApiException(e);
        }
    }

    /**
     * 检查微信返回的错误码
     *
     * @param jsonObject JSONObject
     */
    private static void checkErrorCode(JSONObject jsonObject) {
        Integer errcode = jsonObject.getInt("errcode");
        if (errcode != null && errcode != 0) {
            String errmsg = jsonObject.getStr("errmsg");
            log.error("接口调用微信返回失败，失败状态码：{}，失败原因：{}", errcode, errmsg);
            throw new AllApiException(PlatformEnum.WX, errcode, errmsg);
        }
    }

}
