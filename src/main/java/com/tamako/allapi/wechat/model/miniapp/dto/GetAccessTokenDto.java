package com.tamako.allapi.wechat.model.miniapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 */
@Data
@Builder
@AllArgsConstructor
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
