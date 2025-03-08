package com.tamako.allapi.api.impl;


import cn.hutool.json.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.tamako.allapi.ali.enums.nls.ActionEnum;
import com.tamako.allapi.ali.enums.nls.NLSProductEnum;
import com.tamako.allapi.ali.model.nls.dto.GetFileTransResultDto;
import com.tamako.allapi.ali.model.nls.vo.NlsResult;
import com.tamako.allapi.api.AliNLSApi;
import com.tamako.allapi.api.impl.base.AliBaseImpl;
import com.tamako.allapi.configuration.properties.AliProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import com.tamako.allapi.utils.JSONUtil;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentMap;

/**
 * 阿里智能语音交互API实现类
 *
 * @author Tamako
 * @since 2025/1/16 15:46
 */
public class AliNLSImpl extends AliBaseImpl implements AliNLSApi {
    /**
     * 中国站版本
     */
    public static final String API_VERSION = "2018-08-17";

    /**
     * 阿里云鉴权client
     */
    private final ConcurrentMap<NLSProductEnum, IAcsClient> clientMap;

    /**
     * 构造方法
     *
     * @param aliProperties 配置参数
     * @param clientMap     阿里云鉴权client
     */
    public AliNLSImpl(AliProperties aliProperties, ConcurrentMap<NLSProductEnum, IAcsClient> clientMap) {
        super(aliProperties);
        this.clientMap = clientMap;
    }


    /**
     * 提交文件转写请求
     * 主要需要使用taskid进行后续的结果查询
     *
     * @param dto 请求参数
     * @return 请求结果
     */
    @Override
    public NlsResult submitFileTransRequest(GetFileTransResultDto dto) {
        // 创建CommonRequest，设置请求参数。
        CommonRequest postRequest = createRequest(ActionEnum.POST_REQUEST_ACTION, MethodType.POST);
        JSONObject taskObject = JSONUtil.parseObj(dto, true);
        taskObject.set("appkey", aliProperties.getNls().getAppKey());
        // 设置以上JSON字符串为Body参数。
        postRequest.putBodyParameter("Task", taskObject.toString());
        // 提交录音文件识别请求，获取录音文件识别请求任务的ID，以供识别结果查询使用。
        try {
            CommonResponse postResponse = clientMap.get(NLSProductEnum.FILE_TRANS).getCommonResponse(postRequest);
            return JSONUtil.toBean(postResponse.getData(), NlsResult.class);
        } catch (ClientException e) {
            throw new AllApiException(PlatformEnum.ALI, e.getErrCode(), e.getErrMsg());
        }
    }

    /**
     * 获取文件转写结果
     *
     * @param taskId 任务ID
     * @return 转写结果
     */
    @Override
    public NlsResult getFileTransResult(@NotNull String taskId) {
        //创建CommonRequest，设置任务ID。
        CommonRequest getRequest = createRequest(ActionEnum.GET_REQUEST_ACTION, MethodType.GET);
        // 设置任务ID为查询参数
        getRequest.putQueryParameter("TaskId", taskId);
        //提交录音文件识别结果查询请求
        NlsResult result;
        try {
            CommonResponse getResponse = clientMap.get(NLSProductEnum.FILE_TRANS).getCommonResponse(getRequest);
            if (getResponse.getHttpStatus() != 200) {
                throw new AllApiException(PlatformEnum.ALI, "请求失败，HTTP状态码：" + getResponse.getHttpStatus());
            }
            result = JSONUtil.toBean(getResponse.getData(), NlsResult.class);
        } catch (Exception e) {
            throw new AllApiException(PlatformEnum.ALI, e.getMessage());
        }
        return result;
    }


    /**
     * 构建请求请求
     *
     * @param action action
     * @param method 请求方法
     * @return 请求对象
     */
    private @NotNull CommonRequest createRequest(ActionEnum action, MethodType method) {
        CommonRequest request = new CommonRequest();
        // 设置域名
        request.setSysDomain(aliProperties.getNls().getDomain());
        // 设置API版本
        request.setSysVersion(API_VERSION);
        // 设置action
        request.setSysAction(action.getValue());
        // 设置产品名称
        request.setSysProduct(NLSProductEnum.FILE_TRANS.getName());
        // 设置为GET方式的请求
        request.setSysMethod(method);
        //当aliyun-java-sdk-core 版本为4.6.0及以上时，请取消该行注释
        if (method == MethodType.POST) {
            request.setHttpContentType(FormatType.JSON);
        }
        return request;
    }
}
