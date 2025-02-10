package com.tamako.allapi.api.impl;


import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponseBody;
import com.tamako.allapi.api.AliSMSApi;
import com.tamako.allapi.configuration.properties.AliProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import darabonba.core.client.ClientOverrideConfiguration;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * 阿里云短信API实现
 *
 * @author Tamako
 * @since 2024/8/28 11:50
 */
public class AliSMSImpl implements AliSMSApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();
    /**
     * 阿里云短信配置
     */
    private final AliProperties aliProperties;

    /**
     * 构造方法
     *
     * @param aliProperties 阿里云短信配置
     */
    public AliSMSImpl(AliProperties aliProperties) {
        this.aliProperties = aliProperties;
    }


    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    @Override
    public void sendSms(@NotNull String phone, @NotNull String code) {
        try (AsyncClient client = createAsyncClient()) {
            HashMap<String, String> contentParam = new HashMap<>();
            contentParam.put("code", code);
            SendSmsRequest request = SendSmsRequest.builder()
                    .signName(aliProperties.getSms().getSignName())
                    .templateCode(aliProperties.getSms().getTemplateCode())
                    .phoneNumbers(phone)
                    .templateParam(JSONUtil.toJsonStr(contentParam))
                    .build();
            CompletableFuture<SendSmsResponse> responseFuture = client.sendSms(request);
            SendSmsResponseBody body = responseFuture.get().getBody();
            if (!"OK".equalsIgnoreCase(body.getCode())) {
                log.error("发送登录短信验证码失败：状态码-{}，状态描述-{}", body.getCode(), body.getMessage());
                throw new AllApiException(PlatformEnum.ALI, body.getCode(), body.getMessage());
            }
        } catch (AllApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("发送登录短信验证码失败：", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建阿里云短信异步客户端
     *
     * @return 阿里云短信异步客户端
     */
    private AsyncClient createAsyncClient() {
        Credential credential = Credential.builder()
                .accessKeyId(aliProperties.getAccessKeyId())
                .accessKeySecret(aliProperties.getAccessKeySecret())
                .build();
        StaticCredentialProvider provider = StaticCredentialProvider.create(credential);
        return AsyncClient.builder()
                .region("cn-beijing")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                                .setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();
    }


}
