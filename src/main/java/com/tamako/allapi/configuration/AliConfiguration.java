package com.tamako.allapi.configuration;


import cn.hutool.core.util.StrUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.tamako.allapi.ali.enums.nls.NLSProductEnum;
import com.tamako.allapi.api.AliFCApi;
import com.tamako.allapi.api.AliNLSApi;
import com.tamako.allapi.api.AliOSSApi;
import com.tamako.allapi.api.AliSMSApi;
import com.tamako.allapi.api.impl.AliFCImpl;
import com.tamako.allapi.api.impl.AliNLSApiImpl;
import com.tamako.allapi.api.impl.AliOSSImpl;
import com.tamako.allapi.api.impl.AliSMSImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    public AliOSSApi aliOssApi(AliProperties aliProperties) {
        return new AliOSSImpl(aliProperties);
    }

    /**
     * 阿里云函数计算配置
     *
     * @param aliProperties 阿里云函数计算配置
     * @return AliFCApi
     */
    @Bean
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

        Map<NLSProductEnum, IAcsClient> clientMap = new HashMap<>();
        if (StrUtil.isNotEmpty(regionId)) {
            DefaultProfile.addEndpoint(regionId, NLSProductEnum.FILE_TRANS.getName(),
                    endpointName);
            DefaultProfile profile = DefaultProfile.getProfile(regionId,
                    aliProperties.getAccessKeyId(),
                    aliProperties.getAccessKeySecret());
            IAcsClient client = new DefaultAcsClient(profile);
            clientMap.put(NLSProductEnum.FILE_TRANS, client);
        }
        return new AliNLSApiImpl(aliProperties, clientMap);
    }
}
