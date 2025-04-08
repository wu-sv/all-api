package com.tamako.allapi.api;


import com.tamako.allapi.wechat.model.miniapp.dto.*;
import com.tamako.allapi.wechat.model.miniapp.dto.getorder.GetOrderDto;
import com.tamako.allapi.wechat.model.miniapp.dto.getorder.GetOrderListDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.CommonUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.SimpleUploadShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.UploadCombinedShippingInfoDto;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.UploadShippingInfoDto;
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
     * 上传购物详情（旧版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     * @deprecated
     */
    @Deprecated
    ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull UploadShoppingInfoDto dto);

    /**
     * 上传购物详情(简化版)（旧版）
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShoppingInfoDto
     * @return UploadShoppingInfoVo
     * @deprecated
     */
    @Deprecated
    ResponseVo uploadShoppingInfo(@NotNull String accessToken, @NotNull SimpleUploadShoppingInfoDto dto);

    /**
     * 上传物流信息（旧版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         UploadShippingInfoDto
     * @return UploadShippingInfoVo
     * @deprecated
     */
    @Deprecated
    ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull UploadShippingInfoDto dto);

    /**
     * 上传物流信息(简化版)（旧版）
     * 该接口使用原支付交易对应的微信订单号
     *
     * @param accessToken 接口调用凭证
     * @param dto         SimpleUploadShippingInfoDto
     * @return UploadShippingInfoVo
     * @deprecated
     */
    @Deprecated
    ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull SimpleUploadShippingInfoDto dto);

    /**
     * 发货信息录入接口（新版）
     * 该接口在平台能力处，与上传物流信息接口不同
     *
     * @param accessToken 接口调用凭证
     * @param dto         发货信息录入dto
     * @return ResponseVo
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/platform-capabilities/business-capabilities/order-shipping/order-shipping.html#%E4%B8%80%E3%80%81%E5%8F%91%E8%B4%A7%E4%BF%A1%E6%81%AF%E5%BD%95%E5%85%A5%E6%8E%A5%E5%8F%A3">小程序发货信息管理服务</a>
     */
    ResponseVo uploadShippingInfo(@NotNull String accessToken, @NotNull CommonUploadShippingInfoDto dto);

    /**
     * 发货信息合单录入接口（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         发货信息合单录入dto
     * @return ResponseVo
     */
    ResponseVo uploadCombinedShippingInfo(@NotNull String accessToken, @NotNull UploadCombinedShippingInfoDto dto);

    /**
     * 通过交易单号或商户号+商户单号来查询该支付单的发货状态（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         发货状态查询dto
     * @return GetOrderVo
     */
    GetOrderVo getOrder(@NotNull String accessToken, @NotNull GetOrderDto dto);

    /**
     * 通过支付时间、支付者openid或订单状态来查询订单列表（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         订单列表查询dto
     * @return GetOrderListVo
     */
    GetOrderListVo getOrderList(@NotNull String accessToken, @NotNull GetOrderListDto dto);

    /**
     * 特殊发货报备（新版）
     *
     * @param accessToken 接口调用凭证
     * @param dto         OpSpecialOrderDto
     * @return ResponseVo
     */
    ResponseVo opSpecialOrder(@NotNull String accessToken, @NotNull OpSpecialOrderDto dto);
}
