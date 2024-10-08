package com.tamako.allapi.api;


import com.tamako.allapi.wechat.model.miniapp.dto.*;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.CommonUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.SimpleUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.UploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.SimpleUploadShoppingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.UploadShoppingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.miniapp.vo.JsCode2SessionVo;
import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo.GetPhoneNumberVo;
import com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo.MsgSecCheckVo;
import org.jetbrains.annotations.NotNull;

/**
 * 微信小程序API
 *
 * @author Tamako
 * @since 2024/8/28 13:12
 */
public interface WechatMiniAppApi {
    /**
     * 获取access_token(获取接口调用凭据)(需要注意该access_token需要存入缓存中，避免频繁调用接口)
     * 该接口为最简版本的获取access_token接口，仅供开发者测试使用，正式环境请使用缓存机制，避免频繁调用接口。
     *
     * @param dto GetAccessTokenDto
     * @return GetAccessTokenVo
     */
    GetAccessTokenVo getAccessToken(@NotNull GetAccessTokenDto dto);

    /**
     * 获取access_token
     * 该接口可以直接或access_token,但是用户还需要使用缓存机制
     *
     * @return GetAccessTokenVo
     */
    GetAccessTokenVo getAccessToken();

    /**
     * 小程序登录(通过code换取openId)
     *
     * @param dto JsCode2SessionDto
     * @return JsCode2SessionVo
     */
    JsCode2SessionVo jscode2Session(@NotNull JsCode2SessionDto dto);

    /**
     * 小程序登录(推荐使用)
     * 该接口只需要传入code,不需要传入appid和secret
     *
     * @param jsCode 登录时获取的 code
     * @return JsCode2SessionVo
     */
    JsCode2SessionVo jscode2Session(@NotNull String jsCode);

    /**
     * 手机号快速验证
     *
     * @param accessToken access_token
     * @param code        手机号获取凭证
     * @return GetPhoneNumberVo
     */
    GetPhoneNumberVo getPhoneNumber(@NotNull String accessToken, @NotNull String code);

    /**
     * 获取不限制的小程序码
     * 该接口用于获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制
     *
     * @param accessToken 接口调用凭证
     * @param dto         GetUnlimitedQRCodeDto
     * @return GetUnlimitedQRCodeVo
     */
    byte[] getUnlimitedQrCode(@NotNull String accessToken, @NotNull GetUnlimitedQRCodeDto dto);

    /**
     * 发送订阅消息
     *
     * @param accessToken 接口调用凭证，该参数为 URL 参数，非 Body 参数
     * @param dto         SendMessageDto
     * @return SendMessageVo
     */
    ResponseVo sendMessage(@NotNull String accessToken, @NotNull SendMessageDto dto);

    /**
     * 文本内容安全识别
     *
     * @param accessToken 接口调用凭证
     * @param dto         MsgSecCheckDto
     * @return MsgSecCheckVo
     */
    MsgSecCheckVo msgSecCheck(@NotNull String accessToken, @NotNull MsgSecCheckDto dto);

    /**
     * 上传购物详情（微信电商使用）
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     */
    ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull UploadShoppingInfoDto dto);

    /**
     * 上传购物详情(推荐,简化版)（微信电商使用）
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     */
    ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull SimpleUploadShoppingInfoDto dto);

    /**
     * 上传物流信息（微信电商使用）
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShippingInfoDto
     * @return UploadShippingInfoVo
     */
    ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull UploadShippingInfoDto dto);

    /**
     * 上传物流信息(推荐,简化版)（微信电商使用）
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShippingInfoDto
     * @return UploadShippingInfoVo
     */
    ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull SimpleUploadShippingInfoDto dto);

    /**
     * 发货信息录入接口（普通商户使用）
     * 该接口在平台能力处，与上传物流信息接口不同
     *
     * @param accessToken 接口调用凭证
     * @param dto         发货信息录入dto
     * @return ResponseVo
     */
    ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull CommonUploadShippingInfoDto dto);
}
