package com.tamako.allapi.api.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.json.JSONObject;
import com.tamako.allapi.api.WechatMiniAppApi;
import com.tamako.allapi.api.impl.base.WeChatBaseImpl;
import com.tamako.allapi.configuration.properties.WechatProperties;
import com.tamako.allapi.utils.JSONUtil;
import com.tamako.allapi.utils.network.WeChatNetWorkUtil;
import com.tamako.allapi.wechat.constants.MiniAppUrlConstant;
import com.tamako.allapi.wechat.enums.miniapp.uploadshop.OrderNumberTypeEnum;
import com.tamako.allapi.wechat.model.miniapp.dto.*;
import com.tamako.allapi.wechat.model.miniapp.dto.getorder.GetOrderDto;
import com.tamako.allapi.wechat.model.miniapp.dto.getorder.GetOrderListDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.CommonUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.SimpleUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.UploadCombinedShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.UploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.Payer;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.SimpleUploadShoppingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.UploadShoppingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.miniapp.vo.JsCode2SessionVo;
import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import com.tamako.allapi.wechat.model.miniapp.vo.getorder.GetOrderListVo;
import com.tamako.allapi.wechat.model.miniapp.vo.getorder.GetOrderVo;
import com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo.GetPhoneNumberVo;
import com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo.MsgSecCheckVo;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序API实现类
 *
 * @author Tamako
 */
public class WechatMiniAppImpl extends WeChatBaseImpl implements WechatMiniAppApi {
    /**
     * 构造方法
     *
     * @param wechatProperties 微信配置
     */
    public WechatMiniAppImpl(WechatProperties wechatProperties) {
        super(wechatProperties);
    }

    /**
     * 获取access_token(获取接口调用凭据)(需要注意该access_token需要存入缓存中，避免频繁调用接口)
     * 该接口为最简版本的获取access_token接口，仅供开发者测试使用，正式环境请使用缓存机制，避免频繁调用接口。
     *
     * @param dto GetAccessTokenDto
     * @return GetAccessTokenVo
     */
    @Override
    public GetAccessTokenVo getAccessToken(@NotNull GetAccessTokenDto dto) {
        log.info("获取接口调用凭据");
        UrlBuilder urlBuilder = UrlBuilder.of()
                .setScheme("https")
                .setHost(MiniAppUrlConstant.WECHAT_API_URL)
                .addPath(MiniAppUrlConstant.WECHAT_GET_ACCESS_TOKEN)
                .addQuery("grant_type", "client_credential")
                .addQuery("appid", dto.getAppid())
                .addQuery("secret", dto.getSecret());

        JSONObject jsonObject = WeChatNetWorkUtil.getSync(urlBuilder.build());
        return JSONUtil.toBean(jsonObject, GetAccessTokenVo.class);
    }

    /**
     * 获取access_token
     * 该接口可以直接或access_token,但是用户还需要使用缓存机制
     *
     * @return GetAccessTokenVo
     */
    @Override
    public GetAccessTokenVo getAccessToken() {
        GetAccessTokenDto dto = GetAccessTokenDto.builder()
                .appid(wechatProperties.getAppId())
                .secret(wechatProperties.getSecret())
                .build();
        return getAccessToken(dto);
    }

    /**
     * 小程序登录(通过code换取openId)
     *
     * @param dto JsCode2SessionDto
     * @return JsCode2SessionVo
     */
    @Override
    public JsCode2SessionVo jscode2Session(@NotNull JsCode2SessionDto dto) {
        log.info("小程序登录");
        String url = UrlBuilder.of()
                .setScheme("https")
                .setHost(MiniAppUrlConstant.WECHAT_API_URL)
                .addPath(MiniAppUrlConstant.WECHAT_MINI_LOGIN)
                .addQuery("appid", dto.getAppid())
                .addQuery("secret", dto.getSecret())
                .addQuery("js_code", dto.getJsCode())
                .addQuery("grant_type", "authorization_code")
                .build();
        JSONObject jsonObject = WeChatNetWorkUtil.getSync(url);
        return JSONUtil.toBean(jsonObject, JsCode2SessionVo.class);

    }

    /**
     * 小程序登录(推荐使用)
     * 该接口只需要传入code,不需要传入appid和secret
     *
     * @param jsCode 登录时获取的 code
     * @return JsCode2SessionVo
     */
    @Override
    public JsCode2SessionVo jscode2Session(@NotNull String jsCode) {
        JsCode2SessionDto dto = JsCode2SessionDto.builder()
                .appid(wechatProperties.getAppId())
                .secret(wechatProperties.getSecret())
                .jsCode(jsCode)
                .build();
        return this.jscode2Session(dto);
    }

    /**
     * 手机号快速验证
     *
     * @param accessToken access_token
     * @param code        手机号获取凭证
     * @return GetPhoneNumberVo
     */
    @Override
    public GetPhoneNumberVo getPhoneNumber(@NotNull String accessToken, @NotNull String code) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.WECHAT_GET_PHONE_NUMBER, accessToken);
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, params);
        return JSONUtil.toBean(jsonObject, GetPhoneNumberVo.class);
    }

    /**
     * 获取不限制的小程序码
     * 该接口用于获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制
     *
     * @param accessToken 接口调用凭证
     * @param dto         GetUnlimitedQRCodeDto
     * @return GetUnlimitedQRCodeVo
     */
    @Override
    public byte[] getUnlimitedQrCode(@NotNull String accessToken, @NotNull GetUnlimitedQRCodeDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.WECHAT_GET_UNLIMITED_QR_CODE, accessToken);
        JSONObject params = JSONUtil.parseObj2WeChat(dto);
        return WeChatNetWorkUtil.postSyncBytes(url, params);
    }

    /**
     * 发送订阅消息
     *
     * @param accessToken 接口调用凭证，该参数为 URL 参数，非 Body 参数
     * @param dto         SendMessageDto
     * @return SendMessageVo
     */
    @Override
    public ResponseVo sendMessage(@NotNull String accessToken, @NotNull SendMessageDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.WECHAT_SEND_MESSAGE, accessToken);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, JSONUtil.parseObj2WeChat(dto));
        return toResponseVo(jsonObject);
    }

    /**
     * 文本内容安全识别
     *
     * @param accessToken 接口调用凭证
     * @param dto         MsgSecCheckDto
     * @return MsgSecCheckVo
     */
    @Override
    public MsgSecCheckVo msgSecCheck(@NotNull String accessToken, @NotNull MsgSecCheckDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.WECHAT_MSG_SEC_CHECK, accessToken);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, JSONUtil.parseObj2WeChat(dto));
        return JSONUtil.toBean(jsonObject, MsgSecCheckVo.class);
    }

    /**
     * 上传购物详情（旧版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     * @deprecated
     */
    @Override
    @Deprecated
    public ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull UploadShoppingInfoDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.WECHAT_UPLOAD_SHOPPING_INFO, accessToken);
        JSONObject body = JSONUtil.parseObj2WeChat(dto);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, body);
        return toResponseVo(jsonObject);
    }

    /**
     * 上传购物详情(推荐,简化版)（旧版）
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     * @deprecated
     */
    @Override
    @Deprecated
    public ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull SimpleUploadShoppingInfoDto dto) {
        OrderKey orderKey = new OrderKey();
        orderKey.setOrderNumberType(OrderNumberTypeEnum.WECHAT_PAYMENT_NUMBER.getCode());
        orderKey.setTransactionId(dto.getTransactionId());
        Payer payer = new Payer();
        payer.setOpenid(dto.getOpenId());
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        UploadShoppingInfoDto uploadShoppingInfoDto = UploadShoppingInfoDto.builder()
                .orderKey(orderKey)
                .payer(payer)
                .uploadTime(zonedDateTime)
                .logisticsType(dto.getLogisticsType())
                .orderList(dto.getOrderList())
                .build();
        return uploadShoppingInfo(accessToken, uploadShoppingInfoDto);
    }

    /**
     * 上传物流信息（旧版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShippingInfoDto
     * @return UploadShippingInfoVo
     * @deprecated
     */
    @Override
    @Deprecated
    public ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull UploadShippingInfoDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.WECHAT_UPLOAD_SHIPPING_INFO, accessToken);
        JSONObject body = JSONUtil.parseObj2WeChat(dto);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, body);
        return toResponseVo(jsonObject);
    }

    /**
     * 上传物流信息(推荐,简化版)（旧版）
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShippingInfoDto
     * @return UploadShippingInfoVo
     * @deprecated
     */
    @Override
    @Deprecated
    public ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull SimpleUploadShippingInfoDto dto) {
        OrderKey orderKey = new OrderKey();
        orderKey.setOrderNumberType(OrderNumberTypeEnum.WECHAT_PAYMENT_NUMBER.getCode());
        orderKey.setTransactionId(dto.getTransactionId());
        ZonedDateTime now = ZonedDateTime.now();
        UploadShippingInfoDto uploadShippingInfoDto = UploadShippingInfoDto.builder()
                .orderKey(orderKey)
                .deliveryMode(dto.getDeliveryMode())
                .shippingList(dto.getShippingList())
                .build();
        uploadShippingInfoDto.setUploadTime(now);
        return uploadShippingInfo(accessToken, uploadShippingInfoDto);
    }

    /**
     * 发货信息录入接口（新版）
     * 该接口在平台能力处，与上传物流信息接口不同
     *
     * @param accessToken 接口调用凭证
     * @param dto         发货信息录入dto
     * @return ResponseVo
     */
    @Override
    public ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull CommonUploadShippingInfoDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.WECHAT_COMMON_UPLOAD_SHIPPING_INFO, accessToken);
        JSONObject body = JSONUtil.parseObj2WeChat(dto);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, body);
        return toResponseVo(jsonObject);
    }

    /**
     * 发货信息合单录入接口（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         发货信息合单录入dto
     * @return ResponseVo
     */
    @Override
    public ResponseVo uploadCombinedShippingInfo(@NotNull String accessToken, @NotNull UploadCombinedShippingInfoDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.UPLOAD_COMBINED_SHIPPING_INFO, accessToken);
        JSONObject body = JSONUtil.parseObj2WeChat(dto);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, body);
        return toResponseVo(jsonObject);
    }

    /**
     * 通过交易单号或商户号+商户单号来查询该支付单的发货状态（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         发货状态查询dto
     * @return GetOrderVo
     */
    @Override
    public GetOrderVo getOrder(@NotNull String accessToken, @NotNull GetOrderDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.GET_ORDER, accessToken);
        JSONObject body = JSONUtil.parseObj2WeChat(dto);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, body);
        return JSONUtil.toBean(jsonObject, GetOrderVo.class);
    }

    /**
     * 通过支付时间、支付者openid或订单状态来查询订单列表（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         订单列表查询dto
     * @return GetOrderListVo
     */
    @Override
    public GetOrderListVo getOrderList(@NotNull String accessToken, @NotNull GetOrderListDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.GET_ORDER_LIST, accessToken);
        JSONObject body = JSONUtil.parseObj2WeChat(dto);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, body);
        return JSONUtil.toBean(jsonObject, GetOrderListVo.class);
    }

    /**
     * 特殊发货报备（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         OpSpecialOrderDto
     * @return ResponseVo
     */
    @Override
    public ResponseVo opSpecialOrder(@NotNull String accessToken, @NotNull OpSpecialOrderDto dto) {
        String url = createUrlWithAccessToken(MiniAppUrlConstant.OP_SPECIAL_ORDER, accessToken);
        JSONObject body = JSONUtil.parseObj2WeChat(dto);
        JSONObject jsonObject = WeChatNetWorkUtil.postSync(url, body);
        return toResponseVo(jsonObject);
    }
}