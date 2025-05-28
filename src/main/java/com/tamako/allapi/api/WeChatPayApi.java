package com.tamako.allapi.api;


import com.tamako.allapi.wechat.model.wxpay.dto.MiniAppPayOrderDto;
import com.tamako.allapi.wechat.model.wxpay.vo.WxPayOrderInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * 微信支付API
 *
 * @author Tamako
 * @since 2024/8/28 13:13
 */
public interface WeChatPayApi {
    /**
     * 小程序支付下单(JSAPI)
     *
     * @param dto 订单信息
     * @return 支付结果
     */
    Map<String, String> miniAppPayOrder(MiniAppPayOrderDto dto);

    /**
     * 小程序支付结果回调通知(JSAPI)
     *
     * @param request  请求
     * @param response 响应
     * @return 通知结果
     */
    WxPayOrderInfo miniAppPayNotify(HttpServletRequest request, HttpServletResponse response);


    /**
     * 商户订单号查询订单
     * 推荐使用方法：在微信支付时将单号存到redis中，
     * 在接收到回调信息后删除redis中的信息
     * 定时判断redis中是否有订单，如果有，则查询订单
     *
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    WxPayOrderInfo orderQueryByOutTradeNo(String outTradeNo);

    /**
     * 微信支付订单号查询订单
     *
     * @param transactionId 微信交易订单号
     * @return 订单信息
     */
    WxPayOrderInfo orderQueryByTransactionId(String transactionId);

    /**
     * 关闭订单
     * 未支付状态的订单，可在无需支付时调用此接口关闭订单
     *
     * @param outTradeNo 商户订单号
     */
    void closeOrderByOutTradeNo(String outTradeNo);

}
