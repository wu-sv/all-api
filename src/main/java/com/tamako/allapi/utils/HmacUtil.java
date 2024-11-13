package com.tamako.allapi.utils;


import cn.hutool.core.util.HexUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * HMAC加密工具类
 *
 * @author Tamako
 * @since 2024/11/12 16:10
 */
public class HmacUtil {
    /**
     * HMAC加密(hmac-sha256)
     *
     * @param keyString 密钥
     * @param msg       待加密消息
     * @return 加密后的消息
     */
    public static byte[] hmacSign(String keyString, byte[] msg) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);
        return mac.doFinal(msg);
    }

    public static byte[] hmacSign(byte[] keyString, String msg) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec keySpec = new SecretKeySpec(keyString, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);
        return mac.doFinal(msg.getBytes());
    }

    /**
     * HMAC加密(hmac-sha256)
     *
     * @param keyString 密钥
     * @param msg       待加密消息
     * @return 加密后的消息
     */
    public static String hmacSign2Hex(byte[] keyString, String msg) throws InvalidKeyException, NoSuchAlgorithmException {
        return HexUtil.encodeHexStr(hmacSign(keyString, msg));
    }
}
