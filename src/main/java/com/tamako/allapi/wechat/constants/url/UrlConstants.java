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
     * 获取access_token(获取接口调用凭据)
     */
    public static final String WECHAT_GET_ACCESS_TOKEN_URL =WECHAT_API_URL+"/cgi-bin/token";


    /**
     * 小程序登录
     */
    public static final String WECHAT_MINI_LOGIN_URL = WECHAT_API_URL + "/sns/jscode2session";
}
