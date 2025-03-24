package com.tamako.allapi.configuration;


import cn.hutool.core.util.StrUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.tamako.allapi.ali.enums.nls.NLSProductEnum;
import com.tamako.allapi.api.*;
import com.tamako.allapi.api.impl.*;
import com.tamako.allapi.configuration.properties.AliProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 阿里云配置类
 *
 * @author Tamako
 * @since 2024/8/30 14:50
 */
@Configuration
@EnableConfigurationProperties(AliProperties.class)
public class AliConfiguration {
    /**
     * 阿里云短信配置
     *
     * @param aliProperties 阿里云短信配置
     * @return AliSMSApi
     */
    @Bean
    @ConditionalOnProperty(prefix = "ali.sms", name = "sign-name")
    public AliSMSApi aliSmsApi(AliProperties aliProperties) {
        return new AliSMSImpl(aliProperties);
    }

    /**
     * 阿里云OSS配置
     *
     * @param aliProperties 阿里云OSS配置
     * @return AliOSSApi
     */
    @Bean
    @ConditionalOnProperty(prefix = "ali.oss", name = "bucket-name")
    public AliOSSApi aliOssApi(AliProperties aliProperties) {
        String endpoint = aliProperties.getOss().getEndpoint();
        if (!endpoint.startsWith("http://") && !endpoint.startsWith("https://")) {
            endpoint="https://"+endpoint;
            aliProperties.getOss().setEndpoint(endpoint);
        }
        return new AliOSSImpl(aliProperties);
    }

    /**
     * 阿里云函数计算配置
     *
     * @param aliProperties 阿里云函数计算配置
     * @return AliFCApi
     */
    @Bean
    @ConditionalOnProperty(prefix = "ali.fc", name = "zip-oss-url")
    public AliFCApi aliFcApi(AliProperties aliProperties) {
        return new AliFCImpl(aliProperties);
    }

    /**
     * 阿里云语音合成配置
     *
     * @param aliProperties 阿里云语音合成配置
     * @return AliNLSApi
     */
    @Bean
    @ConditionalOnProperty(prefix = "ali.nls", name = "app-key")
    public AliNLSApi aliNlsApi(AliProperties aliProperties) {
        AliProperties.AliNLS nls = aliProperties.getNls();
        String regionId = nls.getRegionId();
        String endpointName = nls.getEndpointName();
        String domain = nls.getDomain();
        //适配参数
        if (StrUtil.isNotEmpty(regionId) && StrUtil.isEmpty(endpointName)) {
            endpointName = regionId;
            nls.setEndpointName(endpointName);
        }
        //适配参数
        if (StrUtil.isNotEmpty(regionId) && StrUtil.isEmpty(domain)) {
            domain = "filetrans." + regionId + ".aliyuncs.com";
            nls.setDomain(domain);
        }
        aliProperties.setNls(nls);

        ConcurrentMap<NLSProductEnum, IAcsClient> clientMap = new ConcurrentHashMap<>();
        if (StrUtil.isNotEmpty(regionId)) {
            DefaultProfile.addEndpoint(regionId, NLSProductEnum.FILE_TRANS.getName(),
                    endpointName);
            DefaultProfile profile = DefaultProfile.getProfile(regionId,
                    aliProperties.getAccessKeyId(),
                    aliProperties.getAccessKeySecret());
            IAcsClient client = new DefaultAcsClient(profile);
            clientMap.put(NLSProductEnum.FILE_TRANS, client);
        }
        return new AliNLSImpl(aliProperties, clientMap);
    }

    /**
     * 阿里云百炼大模型配置
     *
     * @param aliProperties 阿里云百炼大模型配置
     * @return AliBaiLianApi
     */
    @Bean
    @ConditionalOnProperty(prefix = "ali.bai-lian", name = "api-key")
    public AliBaiLianApi aliBaiLianApi(AliProperties aliProperties) {
        return new AliBaiLianImpl(aliProperties);
    }
}
