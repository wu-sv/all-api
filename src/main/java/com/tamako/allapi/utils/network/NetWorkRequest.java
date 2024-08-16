package com.tamako.allapi.utils.network;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tamako
 * @data 2024/8/16 11:36
 */
@Slf4j
public class NetWorkRequest {
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    /**
     * 同步GET请求
     *
     * @param url 请求地址
     * @return JSONObject
     */
    public static JSONObject getSync(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return handleResponse(client, request);
    }

    /**
     * 异步GET请求
     *
     * @param url 请求地址
     */
    public static void getAsync(String url) {
        //异步请求
        OkHttpClient httpClient = new OkHttpClient();
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .build();
        //准备好请求的Call对象
        Call call = httpClient.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.info("response code:{},response body:{}", response.code(), response.body());
                ResponseBody body = response.body();
                if (body != null) {
                    String result = body.string();
                    log.info("response result:{}", result);
                }
            }
        });
    }

    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param headers     请求头
     * @param requestBody 请求体
     * @return JSONObject
     */
    public static JSONObject postSync(@NotNull String url, Headers headers, @NotNull RequestBody requestBody) {
        OkHttpClient client = new OkHttpClient();
        Request request;
        if (headers != null) {
            request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        }
        return handleResponse(client, request);
    }

    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param requestBody 请求体
     * @return JSONObject
     */
    public static JSONObject postSync(String url, RequestBody requestBody) {
        return postSync(url, null, requestBody);
    }


    /**
     * 同步POST请求
     *
     * @param url         请求地址
     * @param headers     请求头
     * @param requestBody 请求体
     * @return JSONObject
     */
    public static JSONObject postSync(@NotNull String url, Map<String, String> headers, @NotNull Map<String, String> requestBody) {
        Headers headers1 = null;
        if (headers != null) {
            headers1 = Headers.of(headers);
        }
        String body = JSONUtil.toJsonStr(requestBody);
        RequestBody requestBody1 = RequestBody.create(body, JSON_TYPE);
        return postSync(url, headers1, requestBody1);
    }


    /**
     * 异步POST请求
     *
     * @param url         请求地址
     * @param headers     请求头
     * @param requestBody 请求体
     */
    public static void postAsync(String url, Headers headers, RequestBody requestBody) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .post(requestBody)
                .headers(headers)
                .url(url)
                .build();
        //准备好请求的Call对象
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.info("response code:{},response body:{}", response.code(), response.body());
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }

    /**
     * 处理响应数据
     *
     * @param client  OkHttpClient
     * @param request Request
     * @return JSONObject
     */
    private static JSONObject handleResponse(OkHttpClient client, Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if(body==null){
                    log.error("response body is null");
                    throw new RuntimeException("response body is null");
                }
                return JSONUtil.parseObj(body.string());
            }else{
                log.error("response code:{},response body:{}", response.code(), response.body());
                throw new RuntimeException("response code:" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
