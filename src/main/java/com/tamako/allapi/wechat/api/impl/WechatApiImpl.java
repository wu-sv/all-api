package com.tamako.allapi.wechat.api.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.utils.network.NetWorkRequest;
import com.tamako.allapi.wechat.api.WeChatApi;
import com.tamako.allapi.wechat.constants.url.UrlConstants;
import com.tamako.allapi.wechat.model.dto.GetAccessTokenDto;
import com.tamako.allapi.wechat.model.dto.Jscode2SessionDto;
import com.tamako.allapi.wechat.model.vo.GetAccessTokenVo;
import com.tamako.allapi.wechat.model.vo.Jscode2SessionVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author Tamako
 * @data 2024/8/16 11:05
 */
@Slf4j
public class WechatApiImpl implements WeChatApi {

    @Override
    public GetAccessTokenVo getAccessToken(GetAccessTokenDto dto) {
        log.info("获取接口调用凭据");
        String url = StrUtil.format(UrlConstants.WECHAT_GET_ACCESS_TOKEN_URL + "?grant_type={}&appid={}&&secret={}",dto.getGrantType(), dto.getAppid(), dto.getSecret());
        Response response = NetWorkRequest.getSync(url);
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            try {
                String json = null;
                GetAccessTokenVo vo = new GetAccessTokenVo();
                if (body != null) {
                    json = body.string();
                    JSONObject jsonObject = JSONUtil.parseObj(json);
                    vo.setAccessToken(jsonObject.getStr("access_token"));
                    vo.setExpiresIn(jsonObject.getInt("expires_in"));
                }
                return vo;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("获取微信accesstoken失败，失败原因：{}", response.body());
            return null;
        }
    }

    @Override
    public Jscode2SessionVo jscode2Session(Jscode2SessionDto dto) {
        log.info("小程序登录");
        String url = StrUtil.format(UrlConstants.WECHAT_MINI_LOGIN_URL + "?appid={}&secret={}&js_code={}&grant_type={}", dto.getAppid(), dto.getSecret(),dto.getJsCode(),dto.getGrantType());
        Response response = NetWorkRequest.getSync(url);
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            try {
                String json = null;
                Jscode2SessionVo vo = new Jscode2SessionVo();
                if (body != null) {
                    json = body.string();
                    JSONObject jsonObject = JSONUtil.parseObj(json);
                    vo.setSessionKey(jsonObject.getStr("session_key"));
                    vo.setUnionId(jsonObject.getStr("unionid"));
                    vo.setErrmsg(jsonObject.getStr("errmsg"));
                    vo.setOpenid(jsonObject.getStr("openid"));
                    vo.setErrcode(jsonObject.getInt("errcode"));
                    if(vo.getErrcode() != 0){
                        log.error("小程序登录失败，失败状态码：{}，失败原因：{}",vo.getErrcode(),vo.getErrmsg());
                        return null;
                    }
                }
                return vo;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("获取微信accesstoken失败，失败原因：{}", response.body());
            return null;
        }
    }


}
