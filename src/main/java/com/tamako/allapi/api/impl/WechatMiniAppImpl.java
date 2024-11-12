package com.tamako.allapi.api.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.api.WechatMiniAppApi;
import com.tamako.allapi.configuration.WechatProperties;
import com.tamako.allapi.utils.NetWorkToWeChatUtil;
import com.tamako.allapi.wechat.constants.MiniAppUrlConstant;
import com.tamako.allapi.wechat.enums.miniapp.uploadshop.OrderNumberTypeEnum;
import com.tamako.allapi.wechat.model.miniapp.dto.*;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.CommonUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.SimpleUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.UploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.Payer;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.SimpleUploadShoppingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.UploadShoppingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.miniapp.vo.JsCode2SessionVo;
import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
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
public class WechatMiniAppImpl implements WechatMiniAppApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();
    /**
     * 微信配置
     */

    private final WechatProperties wechatProperties;

    public WechatMiniAppImpl(WechatProperties wechatProperties) {
        this.wechatProperties = wechatProperties;
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

        JSONObject jsonObject = NetWorkToWeChatUtil.getSync(urlBuilder.build());
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
        JSONObject jsonObject = NetWorkToWeChatUtil.getSync(url);
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
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstant.WECHAT_GET_PHONE_NUMBER, accessToken).build();
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        JSONObject jsonObject = NetWorkToWeChatUtil.postSync(url, params);
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
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstant.WECHAT_GET_UNLIMITED_QR_CODE, accessToken).build();
        JSONObject params = JSONUtil.parseObj(dto, true);
        return NetWorkToWeChatUtil.postSyncBytes(url, params);
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
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstant.WECHAT_SEND_MESSAGE, accessToken).build();
        JSONObject jsonObject = NetWorkToWeChatUtil.postSync(url, JSONUtil.parseObj(dto, true));
        return JSONUtil.toBean(jsonObject, ResponseVo.class);
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
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstant.WECHAT_MSG_SEC_CHECK, accessToken).build();
        JSONObject jsonObject = NetWorkToWeChatUtil.postSync(url, JSONUtil.parseObj(dto, true));
        return JSONUtil.toBean(jsonObject, MsgSecCheckVo.class);
    }

    /**
     * 上传购物详情
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     */
    @Override
    public ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull UploadShoppingInfoDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstant.WECHAT_UPLOAD_SHOPPING_INFO, accessToken).build();
        JSONObject body = JSONUtil.parseObj(dto, true).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        JSONObject jsonObject = NetWorkToWeChatUtil.postSync(url, body);
        return JSONUtil.toBean(jsonObject, ResponseVo.class);
    }

    /**
     * 上传购物详情(推荐,简化版)
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     */
    @Override
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
     * 上传物流信息
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShippingInfoDto
     * @return UploadShippingInfoVo
     */
    @Override
    public ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull UploadShippingInfoDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstant.WECHAT_UPLOAD_SHIPPING_INFO, accessToken).build();
        JSONObject body = JSONUtil.parseObj(dto, true).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        JSONObject jsonObject = NetWorkToWeChatUtil.postSync(url, body);
        return JSONUtil.toBean(jsonObject, ResponseVo.class);
    }

    /**
     * 上传物流信息(推荐,简化版)
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShippingInfoDto
     * @return UploadShippingInfoVo
     */
    @Override
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

    @Override
    public ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull CommonUploadShippingInfoDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstant.WECHAT_COMMON_UPLOAD_SHIPPING_INFO, accessToken).build();
        JSONObject body = JSONUtil.parseObj(dto, true).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        JSONObject jsonObject = NetWorkToWeChatUtil.postSync(url, body);
        return JSONUtil.toBean(jsonObject, ResponseVo.class);
    }

    /**
     * 创建UrlBuilder
     *
     * @param path        path
     * @param accessToken access_token
     * @return UrlBuilder
     */
    private UrlBuilder createUrlBuilderWithAccessToken(@NotNull String path, @NotNull String accessToken) {
        return UrlBuilder.of()
                .setScheme("https")
                .setHost(MiniAppUrlConstant.WECHAT_API_URL)
                .addPath(path)
                .addQuery("access_token", accessToken);
    }


}