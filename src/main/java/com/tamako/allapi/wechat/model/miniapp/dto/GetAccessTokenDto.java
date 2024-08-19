package com.tamako.allapi.wechat.model.miniapp.dto;


import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @data 2024/8/16 11:21
 */
@Data
public class GetAccessTokenDto {
    /**
     * 微信小程序 appid
     */
    @NotNull
    private String appid;
    /**
     * 微信小程序 secret
     */
    @NotNull
    private String secret;

}
