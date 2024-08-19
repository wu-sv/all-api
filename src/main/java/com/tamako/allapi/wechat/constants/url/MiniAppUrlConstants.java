package com.tamako.allapi.wechat.constants.url;


/**
 * @author Tamako
 * @data 2024/8/16 10:45
 */
public class MiniAppUrlConstants {
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

    /**
     * 获取手机号
     */
    public static final String WECHAT_GET_PHONE_NUMBER_URL = WECHAT_API_URL + "/wxa/business/getuserphonenumber";

    /**
     * 获取不限制的小程序码
     */
    public static final String WECHAT_GET_UNLIMITED_QR_CODE = WECHAT_API_URL + "/wxa/getwxacodeunlimit";

    /**
     * 发送订阅消息
     */
    public static final String WECHAT_SEND_MESSAGE = WECHAT_API_URL + "/cgi-bin/message/subscribe/send";
}
