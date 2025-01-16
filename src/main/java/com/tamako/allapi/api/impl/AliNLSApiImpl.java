package com.tamako.allapi.api.impl;


import cn.hutool.json.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.tamako.allapi.ali.enums.nls.NLSProductEnum;
import com.tamako.allapi.ali.model.nls.dto.GetFileTransResultDto;
import com.tamako.allapi.ali.model.nls.vo.BaseResult;
import com.tamako.allapi.api.AliNLSApi;
import com.tamako.allapi.configuration.AliProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import com.tamako.allapi.utils.JSONUtil;

import java.util.Map;

/**
 * 阿里智能语音交互API实现类
 *
 * @author Tamako
 * @since 2025/1/16 15:46
 */
public class AliNLSApiImpl implements AliNLSApi {
    // 中国站版本
    public static final String API_VERSION = "2018-08-17";
    public static final String POST_REQUEST_ACTION = "SubmitTask";
    public static final String GET_REQUEST_ACTION = "GetTaskResult";

    /**
     * 阿里云OSS配置
     */
    private final AliProperties properties;
    /**
     * 阿里云鉴权client
     */
    private final Map<NLSProductEnum, IAcsClient> clientMap;

    public AliNLSApiImpl(AliProperties properties, Map<NLSProductEnum, IAcsClient> clientMap) {
        this.properties = properties;
        this.clientMap = clientMap;
    }


    @Override
    public BaseResult getFileTransResult(GetFileTransResultDto dto) {
        // 创建CommonRequest，设置请求参数。
        CommonRequest postRequest = new CommonRequest();
        // 设置域名
        postRequest.setSysDomain(properties.getNls().getDomain());
        // 设置API的版本号，格式为YYYY-MM-DD。
        postRequest.setSysVersion(API_VERSION);
        // 设置action
        postRequest.setSysAction(POST_REQUEST_ACTION);
        // 设置产品名称
        postRequest.setSysProduct(NLSProductEnum.FILE_TRANS.getName());
        JSONObject taskObject = JSONUtil.parseObj(dto, true);
        taskObject.set("appkey", properties.getNls().getAppKey());
        // 设置以上JSON字符串为Body参数。
        postRequest.putBodyParameter("Task", taskObject.toString());
        // 设置为POST方式的请求。
        postRequest.setSysMethod(MethodType.POST);
        //当aliyun-java-sdk-core 版本为4.6.0及以上时，请取消该行注释
        postRequest.setHttpContentType(FormatType.JSON);
        // 提交录音文件识别请求，获取录音文件识别请求任务的ID，以供识别结果查询使用。
        try {
            CommonResponse postResponse = clientMap.get(NLSProductEnum.FILE_TRANS).getCommonResponse(postRequest);
            return JSONUtil.toBean(postResponse.getData(), BaseResult.class);
        } catch (ClientException e) {
            throw new AllApiException(PlatformEnum.ALI, e.getErrCode(), e.getErrMsg());
        }
    }
}
