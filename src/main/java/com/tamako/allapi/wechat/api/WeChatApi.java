package com.tamako.allapi.wechat.api;


import com.tamako.allapi.wechat.model.dto.GetAccessTokenDto;
import com.tamako.allapi.wechat.model.dto.GetPhoneNumberDto;
import com.tamako.allapi.wechat.model.dto.Jscode2SessionDto;
import com.tamako.allapi.wechat.model.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.vo.getphonenumbervo.GetPhoneNumberVo;
import com.tamako.allapi.wechat.model.vo.Jscode2SessionVo;

/**
 * @author Tamako
 * @data 2024/8/16 11:05
 */
public interface WeChatApi {
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
     */
    GetPhoneNumberVo getPhoneNumber(GetPhoneNumberDto dto);

}
