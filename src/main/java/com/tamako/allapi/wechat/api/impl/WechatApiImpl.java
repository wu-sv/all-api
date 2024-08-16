package com.tamako.allapi.wechat.api.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.utils.network.NetWorkRequest;
import com.tamako.allapi.wechat.api.WeChatApi;
import com.tamako.allapi.wechat.constants.url.UrlConstants;
import com.tamako.allapi.wechat.model.dto.GetAccessTokenDto;
import com.tamako.allapi.wechat.model.dto.GetPhoneNumberDto;
import com.tamako.allapi.wechat.model.dto.Jscode2SessionDto;
import com.tamako.allapi.wechat.model.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.vo.getphonenumbervo.GetPhoneNumberVo;
import com.tamako.allapi.wechat.model.vo.Jscode2SessionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tamako
 * @data 2024/8/16 11:05
 */
@Slf4j
public class WechatApiImpl implements WeChatApi {

    @Override
    public GetAccessTokenVo getAccessToken(GetAccessTokenDto dto) {
        log.info("获取接口调用凭据");
        String url = new UrlBuilder().setHost(UrlConstants.WECHAT_GET_ACCESS_TOKEN_URL)
                .addQuery("grant_type", "client_credential")
                .addQuery("appid", dto.getAppid())
                .addQuery("secret", dto.getSecret())
                .build();
        JSONObject jsonObject = NetWorkRequest.getSync(url);
        return JSONUtil.toBean(jsonObject, GetAccessTokenVo.class);
    }

    @Override
    public Jscode2SessionVo jscode2Session(Jscode2SessionDto dto) {
        log.info("小程序登录");
        String url = new UrlBuilder().setHost(UrlConstants.WECHAT_MINI_LOGIN_URL)
                .addQuery("appid", dto.getAppid())
                .addQuery("secret", dto.getSecret())
                .addQuery("js_code", dto.getJsCode())
                .addQuery("grant_type", "authorization_code")
                .build();
        JSONObject jsonObject = NetWorkRequest.getSync(url);
        Jscode2SessionVo vo = JSONUtil.toBean(jsonObject, Jscode2SessionVo.class);
        if (vo.getErrcode() != 0) {
            log.error("小程序登录失败，失败状态码：{}，失败原因：{}", vo.getErrcode(), vo.getErrmsg());
            throw new RuntimeException("小程序登录失败，失败状态码：" + vo.getErrcode() + "，失败原因：" + vo.getErrmsg());
        }
        return vo;

    }

    @Override
    public GetPhoneNumberVo getPhoneNumber(GetPhoneNumberDto dto) {
        String url = new UrlBuilder().setHost(UrlConstants.WECHAT_GET_PHONE_NUMBER_URL)
                .addQuery("access_token", dto.getAccessToken())
                .build();
        Map<String, String> params = new HashMap<>();
        params.put("code", dto.getCode());
        JSONObject jsonObject = NetWorkRequest.postSync(url, null, params);
        return JSONUtil.toBean(jsonObject, GetPhoneNumberVo.class);
    }
}