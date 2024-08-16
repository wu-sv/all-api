package com.tamako.allapi.wechat.model.dto;


import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/16 14:20
 */
@Data
public class Jscode2SessionDto {
    /**
     * 小程序 appId
     */
    private String appid;

    /**
     * 小程序 appSecret
     */
    private String secret;

    /**
     * 登录时获取的 code，可通过wx.login获取
     */
    private String jsCode;

}
