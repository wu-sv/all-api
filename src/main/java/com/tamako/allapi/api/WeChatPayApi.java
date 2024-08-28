package com.tamako.allapi.api;


import com.tamako.allapi.wechat.model.wxpay.dto.MiniAppPayOrderDto;
import com.tamako.allapi.wechat.model.wxpay.vo.MiniAppPayNotifyVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * @author Tamako
 * @since 2024/8/28 13:13
 */
public interface WeChatPayApi {
    /**
     * 小程序支付下单
     *
     * @param dto 订单信息
     * @return 支付结果
     */
    Map<String, String> miniAppPayOrder(MiniAppPayOrderDto dto);

    /**
     * 小程序支付结果通知
     *
     * @param request  请求
     * @param response 响应
     * @return 通知结果
     */
    MiniAppPayNotifyVo miniAppPayNotify(HttpServletRequest request, HttpServletResponse response);


    /**
     * 商户订单号查询订单
     * 推荐使用方法：在微信支付时将单号存到redis中，
     * 在接收到回调信息后删除redis中的信息
     * 定时判断redis中是否有订单，如果有，则查询订单
     *
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    MiniAppPayNotifyVo miniAppQueryOrder(String outTradeNo);
}
