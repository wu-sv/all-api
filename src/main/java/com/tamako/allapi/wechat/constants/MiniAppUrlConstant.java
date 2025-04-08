/**
 * 微信常量包
 */
package com.tamako.allapi.wechat.constants;


/**
 * 微信小程序URL常量
 *
 * @author Tamako
 */
public class MiniAppUrlConstant {
    /**
     * 微信API地址
     */
    public static final String WECHAT_API_URL = "api.weixin.qq.com";


    /**
     * 获取access_token(获取接口调用凭据)
     */
    public static final String WECHAT_GET_ACCESS_TOKEN = "/cgi-bin/token";


    /**
     * 小程序登录
     */
    public static final String WECHAT_MINI_LOGIN = "/sns/jscode2session";

    /**
     * 获取手机号
     */
    public static final String WECHAT_GET_PHONE_NUMBER = "/wxa/business/getuserphonenumber";

    /**
     * 获取不限制的小程序码
     */
    public static final String WECHAT_GET_UNLIMITED_QR_CODE = "/wxa/getwxacodeunlimit";

    /**
     * 发送订阅消息
     */
    public static final String WECHAT_SEND_MESSAGE = "/cgi-bin/message/subscribe/send";

    /**
     * 文本内容安全识别
     */
    public static final String WECHAT_MSG_SEC_CHECK = "/wxa/msg_sec_check";

    /**
     * 上传购物详情(旧版)
     *
     * @deprecated
     */
    @Deprecated
    public static final String WECHAT_UPLOAD_SHOPPING_INFO = "/user-order/orders";

    /**
     * 上传物流信息(旧版)
     *
     * @deprecated
     */
    @Deprecated
    public static final String WECHAT_UPLOAD_SHIPPING_INFO = "/user-order/orders/shippings";

    /**
     * 发货信息录入接口（新版）
     */
    public static final String WECHAT_COMMON_UPLOAD_SHIPPING_INFO = "/wxa/sec/order/upload_shipping_info";

    /**
     * 发货信息合单录入接口(新版)
     */
    public static final String UPLOAD_COMBINED_SHIPPING_INFO = "/wxa/sec/order/upload_combined_shipping_info";

    /**
     * 获取订单详情(新版)
     */
    public static final String GET_ORDER = "/wxa/sec/order/get_order";

    /**
     * 查询订单列表(新版)
     */
    public static final String GET_ORDER_LIST = "/wxa/sec/order/get_order_list";

    /**
     *
     */
    public static final String OP_SPECIAL_ORDER = "/wxa/sec/order/opspecialorder";
}
