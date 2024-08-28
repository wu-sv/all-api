package com.tamako.allapi.api;


import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @since 2024/8/28 13:12
 */
public interface AliSMSApi {
    /**
     * 发送短信验证码
     * @param phone 手机号
     */
    void sendSms(@NotNull String phone, @NotNull String code);


}
