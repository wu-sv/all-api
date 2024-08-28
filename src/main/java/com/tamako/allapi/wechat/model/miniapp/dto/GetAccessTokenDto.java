/**
 * 微信小程序dto包
 */
package com.tamako.allapi.wechat.model.miniapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 *
 * 获取微信小程序 access_token 请求参数
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
