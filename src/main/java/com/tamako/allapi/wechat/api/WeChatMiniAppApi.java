package com.tamako.allapi.wechat.api;


import com.tamako.allapi.wechat.model.miniapp.dto.*;
import com.tamako.allapi.wechat.model.miniapp.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.miniapp.vo.Jscode2SessionVo;
import com.tamako.allapi.wechat.model.miniapp.vo.SendMessageVO;
import com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo.GetPhoneNumberVo;

/**
 * @author Tamako
 * @data 2024/8/16 11:05
 */
public interface WeChatMiniAppApi {
    /**
     * 获取access_token(获取接口调用凭据)(需要注意该access_token需要存入缓存中，避免频繁调用接口)
     *
     * @param dto GetAccessTokenDto
     * @return GetAccessTokenVo
     */
    GetAccessTokenVo getAccessToken(GetAccessTokenDto dto);

    /**
     * 小程序登录(通过code换取openId)
     *
     * @param dto Jscode2SessionDto
     * @return Jscode2SessionVo
     */
    Jscode2SessionVo jscode2Session(Jscode2SessionDto dto);


    /**
     * 手机号快速验证
     *
     * @param dto GetPhoneNumberDto
     * @return GetPhoneNumberVo
     */
    GetPhoneNumberVo getPhoneNumber(GetPhoneNumberDto dto);

    /**
     * 获取不限制的小程序码
     * 该接口用于获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制
     *
     * @param dto GetUnlimitedQRCodeDto
     * @return GetUnlimitedQRCodeVo
     */
    byte[] getUnlimitedQRCode(GetUnlimitedQRCodeDto dto);


    /**
     * 发送订阅消息
     *
     * @param dto SendMessageDto
     * @return SendMessageVo
     */
    SendMessageVO sendMessage(SendMessageDto dto);


}
