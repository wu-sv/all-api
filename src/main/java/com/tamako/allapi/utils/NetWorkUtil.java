package com.tamako.allapi.utils;


import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.exception.AllApiException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static cn.hutool.http.HttpRequest.get;
import static cn.hutool.http.HttpRequest.post;

/**
 * @author Tamako
 * @since 2024/11/12 15:03
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
        return getSync(url, null);
    }

    /**
     * 同步GET请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return JSONObject
     */
    public static JSONObject getSync(String url, Map<String, String> headers) {
        HttpRequest request = get(url).addHeaders(headers);
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
     * 处理响应数据
     *
     * @param request Request
     * @return JSONObject
     */
    public static JSONObject handleResponse(HttpRequest request) {
        try (HttpResponse response = request.execute()) {
            String body = response.body();
            if (response.body() == null) {
                log.error("response body is null,please check your request");
                throw new AllApiException("response body is null");
            }
            return JSONUtil.parseObj(body);
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
    public static byte[] handleBytesResponse(HttpRequest request) {
        try (HttpResponse response = request.execute()) {
            byte[] bytes = response.bodyBytes();
            if (bytes == null) {
                log.error("response body is null,pleas check your request");
                throw new AllApiException("response body is null");
            }
            return bytes;
        } catch (AllApiException e) {
            throw e;
        } catch (Exception e) {
            throw new AllApiException(e);
        }
    }
}
