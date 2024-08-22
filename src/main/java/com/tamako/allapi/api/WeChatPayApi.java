package com.tamako.allapi.api;

import com.tamako.allapi.wechat.model.wxpay.dto.MiniAppPayOrderDto;
import com.tamako.allapi.wechat.model.wxpay.vo.MiniAppPayNotifyVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * 该微信支付接口参考于IJPay
 * <a href="https://gitee.com/javen205/IJPay">...</a>
 *
 * @author Tamako
 * @data 2024/8/21 15:29
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
     */
    MiniAppPayNotifyVo miniAppPayNotify(HttpServletRequest request, HttpServletResponse response);

}
