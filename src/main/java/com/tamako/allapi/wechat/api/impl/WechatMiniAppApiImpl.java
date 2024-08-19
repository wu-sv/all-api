package com.tamako.allapi.wechat.api.impl;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.utils.network.NetWorkRequest;
import com.tamako.allapi.wechat.api.WeChatMiniAppApi;
import com.tamako.allapi.wechat.constants.url.MiniAppUrlConstants;
import com.tamako.allapi.wechat.model.miniapp.dto.*;
import com.tamako.allapi.wechat.model.miniapp.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.miniapp.vo.Jscode2SessionVo;
import com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo.MsgSecCheckVo;
import com.tamako.allapi.wechat.model.miniapp.vo.SendMessageVO;
import com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo.GetPhoneNumberVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tamako
 * @data 2024/8/16 11:05
 */
@Slf4j
public class WechatMiniAppApiImpl implements WeChatMiniAppApi {

    @Override
    public GetAccessTokenVo getAccessToken(GetAccessTokenDto dto) {
        log.info("获取接口调用凭据");
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_GET_ACCESS_TOKEN_URL)
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
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_MINI_LOGIN_URL)
                .addQuery("appid", dto.getAppid())
                .addQuery("secret", dto.getSecret())
                .addQuery("js_code", dto.getJsCode())
                .addQuery("grant_type", "authorization_code")
                .build();
        JSONObject jsonObject = NetWorkRequest.getSync(url);
        return JSONUtil.toBean(jsonObject, Jscode2SessionVo.class);

    }

    @Override
    public GetPhoneNumberVo getPhoneNumber(@NotNull String accessToken, @NotNull String code) {
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_GET_PHONE_NUMBER_URL)
                .addQuery("access_token", accessToken)
                .build();
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        JSONObject jsonObject = NetWorkRequest.postSync(url, params);
        return JSONUtil.toBean(jsonObject, GetPhoneNumberVo.class);
    }

    @Override
    public byte[] getUnlimitedQRCode(@NotNull String accessToken,@NotNull GetUnlimitedQRCodeDto dto) {
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_GET_UNLIMITED_QR_CODE)
                .addQuery("access_token", accessToken)
                .build();
        JSONObject params = JSONUtil.parseObj(dto, true);
        return NetWorkRequest.postSyncBytes(url, params);
    }

    @Override
    public SendMessageVO sendMessage(@NotNull String accessToken, @NotNull SendMessageDto dto) {
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_SEND_MESSAGE)
                .addQuery("access_token", accessToken)
                .build();
        JSONObject jsonObject = NetWorkRequest.postSync(url, JSONUtil.parseObj(dto, true));
        SendMessageVO vo = JSONUtil.toBean(jsonObject, SendMessageVO.class);
        if(vo.getErrcode() != 0){
            log.error("发送消息失败，失败状态码：{}，失败原因：{}", vo.getErrcode(), vo.getErrmsg());
            throw new RuntimeException(vo.getErrmsg());
        }
        return vo;
    }

    @Override
    public MsgSecCheckVo msgSecCheck(@NotNull String accessToken, @NotNull MsgSecCheckDto dto) {
        String url = new UrlBuilder().setHost(MiniAppUrlConstants.WECHAT_MSG_SEC_CHECK)
                .addQuery("access_token", accessToken)
                .build();
        JSONObject jsonObject = NetWorkRequest.postSync(url, JSONUtil.parseObj(dto, true));
        return JSONUtil.toBean(jsonObject, MsgSecCheckVo.class);
    }
}