package com.tamako.allapi.utils;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.configuration.VolcEngineProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 火山引擎网络工具类
 *
 * @author Tamako
 * @since 2024/11/13 09:04
 */
@Slf4j
public class NetWork2VolcEngineUtil extends NetWorkUtil {

    /**
     * 同步GET请求
     *
     * @param url        请求地址
     * @param properties 火山引擎配置
     * @return JSONObject
     */
    public static JSONObject get(@NotNull String url, @NotNull VolcEngineProperties properties) {
        try {
            Map<String, String> headers = publicHeaders(url, new HashMap<>(), null, Method.GET, properties);
            return NetWorkUtil.getSync(url, headers);
        } catch (Exception e) {
            throw new AllApiException(PlatformEnum.VOLC_ENGINE, "网络请求失败", e);
        }
    }

    /**
     * 同步POST请求
     *
     * @param url        请求地址
     * @param headers    请求头
     * @param body       请求体
     * @param properties 火山引擎配置
     * @return JSONObject
     */
    public static JSONObject post(@NotNull String url, Map<String, String> headers, @NotNull JSONObject body, @NotNull VolcEngineProperties properties) {
        try {
            headers = publicHeaders(url, headers, body, Method.POST, properties);
            return NetWorkUtil.postSync(url, headers, body);
        } catch (Exception e) {
            throw new AllApiException(PlatformEnum.VOLC_ENGINE, "网络请求失败", e);
        }
    }

    /**
     * 同步POST请求
     *
     * @param url        请求地址
     * @param body       请求体
     * @param properties 火山引擎配置
     * @return JSONObject
     */
    public static JSONObject post(@NotNull String url, @NotNull JSONObject body, @NotNull VolcEngineProperties properties) {
        return post(url, null, body, properties);
    }

    /**
     * 构造公共参数请求头
     *
     * @param url        请求地址
     * @param headers    请求头
     * @param body       请求体
     * @param method     请求方法
     * @param properties 火山引擎配置
     * @return 公共参数请求头
     * @throws NoSuchAlgorithmException 签名异常
     * @throws InvalidKeyException      签名异常
     */
    private static Map<String, String> publicHeaders(String url, Map<String, String> headers, JSONObject body, Method method, VolcEngineProperties properties) throws NoSuchAlgorithmException, InvalidKeyException {
        headers = headers == null ? new HashMap<>() : headers;
        if (Method.POST.equals(method)) {
            headers.put("Content-Type", "application/json");
        }
        headers.put("Host", URLUtil.getHostWithoutProtocol(url));
        headers.put("X-Date", DateTime.now().setTimeZone(TimeZone.getTimeZone("UTC")).toString("yyyyMMdd'T'HHmmss'Z'"));
        if (ObjUtil.isNotNull(body)) {
            String bodyJsonStr = JSONUtil.toJsonStr(body);
            String sha256Hex = DigestUtil.sha256Hex(bodyJsonStr);
            headers.put("X-Content-Sha256", sha256Hex);
        }
        headers.put("Authorization", authorization(url, method, headers, properties));
        return headers;
    }


    /**
     * 构造请求头中的签名
     *
     * @param url        请求地址
     * @param method     请求方法
     * @param headers    请求头
     * @param properties 火山引擎配置
     * @return 签名
     * @throws NoSuchAlgorithmException 签名异常
     * @throws InvalidKeyException      签名异常
     */
    private static String authorization(String url, Method method, Map<String, String> headers, VolcEngineProperties properties) throws NoSuchAlgorithmException, InvalidKeyException {
        //CanonicalRequest = HTTPRequestMethod + '\n' + CanonicalURI + '\n' + CanonicalQueryString + '\n' + CanonicalHeaders + '\n' + SignedHeaders + '\n' + HexEncode(Hash(RequestPayload))
        String canonicalQueryString = URLUtil.getQuery(url);
        //1.构建规范的请求
        String signedHeaders = convertHeaders(headers);
        String canonicalRequest = method.toString() + "\n" +
                "/" + "\n" +
                canonicalQueryString + "\n" +
                signedHeaders + "\n";
        if (Method.POST.equals(method)) {
            canonicalRequest += headers.get("X-Content-Sha256");
        } else if (Method.GET.equals(method)) {
            canonicalRequest += DigestUtil.sha256Hex("");
        }
        //2.构建待签字符串
        //StringToSign = Algorithm + '\n' + RequestDate + '\n' + CredentialScope + '\n' + HexEncode(Hash(CanonicalRequest))
        String requestDate = headers.get("X-Date");
        String date = requestDate.substring(0, 8);
        String host = headers.get("Host");
        String service = host.substring(0, host.indexOf("."));
        String region = "cn-beijing";
        String credentialScope = date + "/" + region + "/" + service + "/request";
        String stringToSign = "HMAC-SHA256" + "\n" +
                requestDate + "\n" +
                credentialScope + "\n" +
                DigestUtil.sha256Hex(canonicalRequest);
        String secretAccessKey = properties.getSecretAccessKey();
        String accessKeyId = properties.getAccessKeyId();
        byte[] kDate = HmacUtil.hmacSign(secretAccessKey.getBytes(), date);
        byte[] kRegion = HmacUtil.hmacSign(kDate, region);
        byte[] kService = HmacUtil.hmacSign(kRegion, service);
        byte[] signingKey = HmacUtil.hmacSign(kService, "request");
        String signature = HmacUtil.hmacSign2Hex(signingKey, stringToSign);
        Map<String, String> authorizationMap = new HashMap<>();
        authorizationMap.put("Credential", accessKeyId + "/" + credentialScope);
        authorizationMap.put("SignedHeaders", signedHeaders.substring(signedHeaders.indexOf("\n\n") + 2));
        authorizationMap.put("Signature", signature);
        return "HMAC-SHA256 " + StrUtil.join(", ", authorizationMap.entrySet());
    }


    /**
     * 转换请求头为规范格式
     *
     * @param headers 请求头
     * @return 规范格式的请求头
     */
    private static String convertHeaders(Map<String, String> headers) {
        StringBuilder canonicalHeaders = new StringBuilder();
        StringBuilder signedHeaders = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey().toLowerCase();
            canonicalHeaders.append(key).append(":").append(entry.getValue()).append("\n");
            signedHeaders.append(key).append(";");
        }
        return canonicalHeaders + "\n" +
                signedHeaders.deleteCharAt(signedHeaders.length() - 1);
    }

    /**
     * 将首字母小写
     *
     * @param str 字符串
     * @return 首字母小写的字符串
     */
    private static String loweCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] chars = str.toCharArray();
            chars[0] += 32;
            return new String(chars);
        }
        return str;
    }
}
