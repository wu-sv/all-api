package com.tamako.allapi.wechat.model.dto;


import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/16 11:21
 */
@Data
public class GetAccessTokenDto {
    /**
     * 微信小程序 appid
     */
    private String appid;
    /**
     * 微信小程序 secret
     */
    private String secret;

}
