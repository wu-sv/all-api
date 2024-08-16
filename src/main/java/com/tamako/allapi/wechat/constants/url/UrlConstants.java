package com.tamako.allapi.wechat.constants.url;


/**
 * @author Tamako
 * @data 2024/8/16 10:45
 */
public class UrlConstants {
    /**
     * 微信API地址
     */
    public static final String WECHAT_API_URL = "https://api.weixin.qq.com";

    /**
     * 微信第三方平台API地址
     */
    public static final String WECHAT_THIRD_PARTY_PLATFORM_URL = WECHAT_API_URL + "/cgi-bin";

    /**
     * 获取access_token
     */
    public static final String WECHAT_GET_ACCESS_TOKEN_URL =WECHAT_THIRD_PARTY_PLATFORM_URL+"/token?grant_type=client_credential";


}
