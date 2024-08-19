package com.tamako.allapi.wechat.model.miniapp.dto;


import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @data 2024/8/16 15:08
 */
@Data
public class GetPhoneNumberDto {
    /**
     * 接口调用凭证，该参数为 URL 参数，非 Body 参数。使用access_token或者authorizer_access_token
     */
    @NotNull
    private String accessToken;

    /**
     * 手机号获取凭证
     */
    @NotNull
    private String code;
}
