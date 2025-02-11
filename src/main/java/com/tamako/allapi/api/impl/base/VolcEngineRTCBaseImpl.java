package com.tamako.allapi.api.impl.base;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.configuration.properties.VolcEngineProperties;
import com.tamako.allapi.utils.JSONUtil;
import com.tamako.allapi.utils.network.NetWork2VolcEngineUtil;
import com.tamako.allapi.volcengine.model.rtc.vo.BaseResult;
import com.tamako.allapi.volcengine.model.rtc.vo.ResponseVo;
import lombok.RequiredArgsConstructor;

/**
 * VolcEngine RTC 基础实现类
 *
 * @author Tamako
 * @since 2024/11/14 14:26
 */
@RequiredArgsConstructor
public class VolcEngineRTCBaseImpl {
    /**
     * 日志
     */
    protected static final Log log = LogFactory.get();
    /**
     * 火山引擎配置
     */
    protected final VolcEngineProperties properties;

    /**
     * 添加appId到请求体中
     *
     * @param body       请求体
     * @return 请求体
     */
    protected JSONObject addAppId(Object body) {
        JSONObject jsonBody = JSONUtil.parseObj(body);
        jsonBody.set("appId", properties.getRtc().getAppId());
        return jsonBody;
    }

    /**
     * post请求
     *
     * @param url        请求地址
     * @param body       请求体
     * @return 响应结果
     */
    protected ResponseVo<BaseResult> post(String url, Object body) {
        JSONObject reqBody = NetWork2VolcEngineUtil.post(url, this.addAppId(body), properties);
        TypeReference<ResponseVo<BaseResult>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }

    /**
     * post请求，返回String结果
     *
     * @param url        请求地址
     * @param body       请求体
     * @return 响应结果
     */
    protected ResponseVo<String> post2String(String url, Object body) {
        JSONObject reqBody = NetWork2VolcEngineUtil.post(url, this.addAppId(body), properties);
        TypeReference<ResponseVo<String>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBeanLowerCase(reqBody, typeReference);
    }
}
