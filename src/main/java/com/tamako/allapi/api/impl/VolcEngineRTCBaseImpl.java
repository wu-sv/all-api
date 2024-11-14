package com.tamako.allapi.api.impl;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import com.tamako.allapi.configuration.VolcEngineProperties;
import com.tamako.allapi.utils.JSONUtil;
import com.tamako.allapi.utils.NetWork2VolcEngineUtil;
import com.tamako.allapi.volcengine.constants.RTCUrlConstant;
import com.tamako.allapi.volcengine.model.rtc.vo.BaseResult;
import com.tamako.allapi.volcengine.model.rtc.vo.ResponseVo;

/**
 * @author Tamako
 * @since 2024/11/14 14:26
 */
public class VolcEngineRTCBaseImpl {
    protected JSONObject addAppId(Object body, VolcEngineProperties properties) {
        JSONObject jsonBody = JSONUtil.parseObj(body);
        jsonBody.set("appId", properties.getRtc().getAppId());
        return jsonBody;
    }

    protected ResponseVo<BaseResult> post(String url, Object body, VolcEngineProperties properties) {
        JSONObject reqBody = NetWork2VolcEngineUtil.post(url, this.addAppId(body, properties), properties);
        TypeReference<ResponseVo<BaseResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }
}
