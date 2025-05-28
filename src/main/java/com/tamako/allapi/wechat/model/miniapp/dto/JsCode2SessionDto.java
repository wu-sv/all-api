package com.tamako.allapi.wechat.model.miniapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * 用于微信登录的请求参数
 *
 * @author Tamako
 */
@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class JsCode2SessionDto {
    /**
     * 小程序 appId
     */
    @NotNull
    private String appid;

    /**
     * 小程序 appSecret
     */
    @NotNull
    private String secret;

    /**
     * 登录时获取的 code，可通过wx.login获取
     */
    @NotNull
    private String jsCode;

}
