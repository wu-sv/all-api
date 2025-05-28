package com.tamako.allapi.api.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.BasePayApiEnum;
import com.ijpay.wxpay.model.v3.Amount;
import com.ijpay.wxpay.model.v3.Payer;
import com.ijpay.wxpay.model.v3.UnifiedOrderModel;
import com.tamako.allapi.api.WeChatPayApi;
import com.tamako.allapi.api.impl.base.WeChatBaseImpl;
import com.tamako.allapi.configuration.properties.WechatProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import com.tamako.allapi.utils.WxPayUtil;
import com.tamako.allapi.utils.network.WeChatNetWorkUtil;
import com.tamako.allapi.wechat.model.wxpay.domin.PayNotifyResponse;
import com.tamako.allapi.wechat.model.wxpay.domin.PlatFromCert;
import com.tamako.allapi.wechat.model.wxpay.dto.MiniAppPayOrderDto;
import com.tamako.allapi.wechat.model.wxpay.vo.WxPayOrderInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付接口实现类
 *
 * @author Tamako
 * @since 2021/1/18 16:22
 */
public class WeChatPayImpl extends WeChatBaseImpl implements WeChatPayApi {
    /**
     * 平台证书相关信息
     */
    private final PlatFromCert platFromCert;

    /**
     * 构造方法
     *
     * @param wechatProperties 微信配置
     * @throws Exception 创建失败
     */
    public WeChatPayImpl(WechatProperties wechatProperties) throws Exception {
        super(wechatProperties);
        PlatFromCert platFromCert = new PlatFromCert();
        WxPayUtil.getSerialNo(platFromCert, wechatProperties.getPay().getCertPath(),
                wechatProperties.getPay().getMchId());
        WxPayUtil.downloadCertificateInfo(platFromCert, wechatProperties.getPay());
        this.platFromCert = platFromCert;
    }

    /**
     * 小程序支付下单
     *
     * @param dto 订单信息
     * @return 支付结果
     */
    @Override
    public Map<String, String> miniAppPayOrder(MiniAppPayOrderDto dto) {
        try {
            String timeExpire = DateUtil.format(dto.getTimeExpire(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Amount amount = new Amount()
                    .setTotal(dto.getAmountTotal());
            Payer payer = new Payer()
                    .setOpenid(dto.getPayerOpenid());
            UnifiedOrderModel model = UnifiedOrderModel.builder()
                    .appid(wechatProperties.getAppId())
                    .mchid(wechatProperties.getPay().getMchId())
                    .description(dto.getDescription())
                    .out_trade_no(dto.getOutTradeNo())
                    .time_expire(timeExpire)
                    .attach(dto.getAttach())
                    .notify_url(wechatProperties.getPay().getNotifyUrl())
                    .amount(amount)
                    .payer(payer)
                    .build();
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.getDomain(),
                    BasePayApiEnum.JS_API_PAY.getUrl(),
                    wechatProperties.getPay().getMchId(),
                    platFromCert.getSerialNo(),
                    null,
                    wechatProperties.getPay().getCertKeyPath(),
                    JSONUtil.toJsonStr(model)
            );
            //根据证书序列号查询对应的证书来验证签名结果
            WxPayUtil.verifySignature(response, platFromCert, wechatProperties.getPay());
            if (response.getStatus() != HttpStatus.HTTP_OK) {
                log.error("微信商户号查询订单失败,状态码：{}, 响应信息：{}", response.getStatus(), response.getBody());
                throw new AllApiException(PlatformEnum.WX, String.valueOf(response.getStatus()), response.getBody());
            }
            JSONObject jsonObject = JSONUtil.parseObj(response.getBody());
            String prepayId = jsonObject.getStr("prepay_id");
            return WxPayKit.jsApiCreateSign(wechatProperties.getAppId(), prepayId, wechatProperties.getPay().getCertKeyPath());
        } catch (Exception e) {
            log.error("微信支付接口调用失败", e);
            throw new AllApiException(PlatformEnum.WX, "微信支付接口调用失败", e);
        }
    }

    /**
     * 小程序支付结果通知
     *
     * @param request  请求
     * @param response 响应
     * @return 通知结果
     */
    @Override
    public WxPayOrderInfo miniAppPayNotify(HttpServletRequest request, HttpServletResponse response) {
        PayNotifyResponse send = new PayNotifyResponse();
        response.setHeader("Content-type", ContentType.JSON.toString());
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");
            String result = WeChatNetWorkUtil.readData(request);
            // 通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wechatProperties.getPay().getMchKey(), platFromCert.getCert());
            //当获取到数据后，进行请求返回
            send.success(response);
            return JSONUtil.toBean(plainText, WxPayOrderInfo.class);
        } catch (Exception e) {
            send.fail(response);
            log.error("微信支付通知接口调用失败", e);
            throw new AllApiException(PlatformEnum.WX, "微信支付通知接口调用失败", e);
        }
    }

    /**
     * 商户订单号查询订单
     * 推荐使用方法：在微信支付时将单号存到redis中，
     * 在接收到回调信息后删除redis中的信息
     * 定时判断redis中是否有订单，如果有，则查询订单
     *
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    @Override
    public WxPayOrderInfo orderQueryByOutTradeNo(String outTradeNo) {
        try {
            Map<String, String> params = new HashMap<>(16);
            params.put("mchid", wechatProperties.getPay().getMchId());
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.getDomain(),
                    String.format(BasePayApiEnum.ORDER_QUERY_BY_OUT_TRADE_NO.getUrl(), outTradeNo),
                    wechatProperties.getPay().getMchId(),
                    platFromCert.getSerialNo(),
                    null,
                    wechatProperties.getPay().getCertKeyPath(),
                    params
            );
            //根据证书序列号查询对应的证书来验证签名结果
            WxPayUtil.verifySignature(response, platFromCert, wechatProperties.getPay());
//            log.info("微信支付接口调用结果：{}", verifySignature);
            if (response.getStatus() != HttpStatus.HTTP_OK) {
                log.error("微信商户号查询订单失败,状态码：{}, 响应信息：{}", response.getStatus(), response.getBody());
                throw new AllApiException(PlatformEnum.WX, String.valueOf(response.getStatus()), response.getBody());
            }
            return JSONUtil.toBean(response.getBody(), WxPayOrderInfo.class);

        } catch (Exception e) {
            log.error("微信商户号查询订单失败", e);
            throw new AllApiException(PlatformEnum.WX, "微信商户号查询订单失败", e);
        }
    }

    @Override
    public WxPayOrderInfo orderQueryByTransactionId(String transactionId) {
        try {
            Map<String, String> params = new HashMap<>(16);
            params.put("mchid", wechatProperties.getPay().getMchId());
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.getDomain(),
                    String.format(BasePayApiEnum.ORDER_QUERY_BY_TRANSACTION_ID.getUrl(), transactionId),
                    wechatProperties.getPay().getMchId(),
                    platFromCert.getSerialNo(),
                    null,
                    wechatProperties.getPay().getCertKeyPath(),
                    params
            );
            //根据证书序列号查询对应的证书来验证签名结果
            WxPayUtil.verifySignature(response, platFromCert, wechatProperties.getPay());
//            log.info("微信支付接口调用结果：{}", verifySignature);
            if (response.getStatus() != HttpStatus.HTTP_OK) {
                log.error("微信支付订单号查询订单失败,状态码：{}, 响应信息：{}", response.getStatus(), response.getBody());
                throw new AllApiException(PlatformEnum.WX, String.valueOf(response.getStatus()), response.getBody());
            }
            return JSONUtil.toBean(response.getBody(), WxPayOrderInfo.class);
        } catch (Exception e) {
            log.error("微信支付订单号查询订单失败", e);
            throw new AllApiException(PlatformEnum.WX, "微信支付订单号查询订单失败", e);
        }
    }

    @Override
    public void closeOrderByOutTradeNo(String outTradeNo) {
        Map<String, String> body = new HashMap<>(16);
        body.put("mchid", wechatProperties.getPay().getMchId());
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.POST,
                    WxDomainEnum.CHINA.getDomain(),
                    String.format(BasePayApiEnum.CLOSE_ORDER_BY_OUT_TRADE_NO.getUrl(), outTradeNo),
                    wechatProperties.getPay().getMchId(),
                    platFromCert.getSerialNo(),
                    null,
                    wechatProperties.getPay().getCertKeyPath(),
                    JSONUtil.toJsonStr(body)
            );
            //根据证书序列号查询对应的证书来验证签名结果
            WxPayUtil.verifySignature(response, platFromCert, wechatProperties.getPay());
            if (response.getStatus() != HttpStatus.HTTP_NO_CONTENT) {
                log.error("关闭订单失败,状态码：{}, 响应信息：{}", response.getStatus(), response.getBody());
                throw new AllApiException(PlatformEnum.WX, String.valueOf(response.getStatus()), response.getBody());
            }
        } catch (Exception e) {
            log.error("关闭订单失败", e);
            throw new AllApiException(PlatformEnum.WX, "关闭订单失败", e);
        }
    }
}
