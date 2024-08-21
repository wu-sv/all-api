package com.tamako.allapi.wechat.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.api.WeChatMiniAppApi;
import com.tamako.allapi.utils.NetWorkRequest;
import com.tamako.allapi.wechat.constants.url.MiniAppUrlConstants;
import com.tamako.allapi.wechat.enumerations.uploadshop.OrderNumberTypeEnum;
import com.tamako.allapi.wechat.model.miniapp.WechatProperties;
import com.tamako.allapi.wechat.model.miniapp.dto.*;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
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
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tamako
 * @data 2024/8/16 11:05
 */
@Slf4j
public class WechatMiniAppApiImpl implements WeChatMiniAppApi {
    @Resource
    private WechatProperties wechatProperties;

    @Override
    public GetAccessTokenVo getAccessToken(GetAccessTokenDto dto) {
        log.info("获取接口调用凭据");
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_GET_ACCESS_TOKEN)
                .addQuery("grant_type", "client_credential")
                .addQuery("appid", dto.getAppid())
                .addQuery("secret", dto.getSecret())
                .build();
        JSONObject jsonObject = NetWorkRequest.getSync(url);
        return JSONUtil.toBean(jsonObject, GetAccessTokenVo.class);
    }

    @Override
    public GetAccessTokenVo getAccessToken() {
        GetAccessTokenDto dto = new GetAccessTokenDto(wechatProperties.getAppId(), wechatProperties.getSecret());
        return getAccessToken(dto);
    }


    @Override
    public JsCode2SessionVo jscode2Session(JsCode2SessionDto dto) {
        log.info("小程序登录");
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_MINI_LOGIN)
                .addQuery("appid", dto.getAppid())
                .addQuery("secret", dto.getSecret())
                .addQuery("js_code", dto.getJsCode())
                .addQuery("grant_type", "authorization_code")
                .build();
        JSONObject jsonObject = NetWorkRequest.getSync(url);
        return JSONUtil.toBean(jsonObject, JsCode2SessionVo.class);

    }

    @Override
    public JsCode2SessionVo jscode2Session(@NotNull String jsCode) {
        JsCode2SessionDto dto = new JsCode2SessionDto(wechatProperties.getAppId(), wechatProperties.getSecret(), jsCode);
        return jscode2Session(dto);
    }

    @Override
    public GetPhoneNumberVo getPhoneNumber(@NotNull String accessToken, @NotNull String code) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstants.WECHAT_GET_PHONE_NUMBER, accessToken).build();
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        JSONObject jsonObject = NetWorkRequest.postSync(url, params);
        return JSONUtil.toBean(jsonObject, GetPhoneNumberVo.class);
    }

    @Override
    public byte[] getUnlimitedQRCode(@NotNull String accessToken, @NotNull GetUnlimitedQRCodeDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstants.WECHAT_GET_UNLIMITED_QR_CODE, accessToken).build();
        JSONObject params = JSONUtil.parseObj(dto, true);
        return NetWorkRequest.postSyncBytes(url, params);
    }

    @Override
    public ResponseVo sendMessage(@NotNull String accessToken, @NotNull SendMessageDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstants.WECHAT_SEND_MESSAGE, accessToken).build();
        JSONObject jsonObject = NetWorkRequest.postSync(url, JSONUtil.parseObj(dto, true));
        ResponseVo vo = JSONUtil.toBean(jsonObject, ResponseVo.class);
        if (vo.getErrcode() != 0) {
            log.error("发送消息失败，失败状态码：{}，失败原因：{}", vo.getErrcode(), vo.getErrmsg());
            throw new RuntimeException(vo.getErrmsg());
        }
        return vo;
    }

    @Override
    public MsgSecCheckVo msgSecCheck(@NotNull String accessToken, @NotNull MsgSecCheckDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstants.WECHAT_MSG_SEC_CHECK, accessToken).build();
        JSONObject jsonObject = NetWorkRequest.postSync(url, JSONUtil.parseObj(dto, true));
        return JSONUtil.toBean(jsonObject, MsgSecCheckVo.class);
    }

    @Override
    public ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull UploadShoppingInfoDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstants.WECHAT_UPLOAD_SHOPPING_INFO, accessToken).build();
        JSONObject body = JSONUtil.parseObj(dto, true).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        JSONObject jsonObject = NetWorkRequest.postSync(url, body);
        return JSONUtil.toBean(jsonObject, ResponseVo.class);
    }

    @Override
    public ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull SimpleUploadShoppingInfoDto dto) {
        OrderKey orderKey = new OrderKey();
        orderKey.setOrderNumberType(OrderNumberTypeEnum.WECHAT_PAYMENT_NUMBER.getCode());
        orderKey.setTransactionId(dto.getTransactionId());
        Payer payer = new Payer();
        payer.setOpenid(dto.getOpenId());
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        UploadShoppingInfoDto uploadShoppingInfoDto = new UploadShoppingInfoDto(orderKey, payer, zonedDateTime);
        uploadShoppingInfoDto.setLogisticsType(dto.getLogisticsType());
        uploadShoppingInfoDto.setOrderList(dto.getOrderList());
        return uploadShoppingInfo(accessToken, uploadShoppingInfoDto);
    }

    @Override
    public ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull UploadShippingInfoDto dto) {
        String url = createUrlBuilderWithAccessToken(MiniAppUrlConstants.WECHAT_UPLOAD_SHIPPING_INFO, accessToken).build();
        JSONObject body = JSONUtil.parseObj(dto, true).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        JSONObject jsonObject = NetWorkRequest.postSync(url, body);
        return JSONUtil.toBean(jsonObject, ResponseVo.class);
    }


    @Override
    public ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull SimpleUploadShippingInfoDto dto) {
        OrderKey orderKey = new OrderKey();
        orderKey.setOrderNumberType(OrderNumberTypeEnum.WECHAT_PAYMENT_NUMBER.getCode());
        orderKey.setTransactionId(dto.getTransactionId());
        ZonedDateTime now = ZonedDateTime.now();
        UploadShippingInfoDto uploadShippingInfoDto = new UploadShippingInfoDto(orderKey, dto.getDeliveryMode(), dto.getShippingList());
        uploadShippingInfoDto.setUploadTime(now);
        return uploadShippingInfo(accessToken, uploadShippingInfoDto);
    }

    private UrlBuilder createUrlBuilderWithAccessToken(@NotNull String url, @NotNull String accessToken) {
        return new UrlBuilder().setHost(url)
                .addQuery("access_token", accessToken);
    }


}