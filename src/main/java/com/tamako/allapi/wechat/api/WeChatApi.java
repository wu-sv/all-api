package com.tamako.allapi.wechat.api;


import com.tamako.allapi.wechat.model.dto.GetAccessTokenDto;
import com.tamako.allapi.wechat.model.vo.GetAccessTokenVo;

/**
 * @author Tamako
 * @data 2024/8/16 11:05
 */
public interface WeChatApi {
    /**
     * 获取access_token(需要注意该access_token需要存入缓存中，避免频繁调用接口)
     * @param dto
     * @return GetAccessTokenVo
     */
    GetAccessTokenVo getAccessToken(GetAccessTokenDto dto);
}
