package com.tamako.allapi.wechat.model.miniapp.dto;


import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
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
