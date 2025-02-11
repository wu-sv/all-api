package com.tamako.allapi.api.impl.base;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.configuration.properties.AliProperties;
import lombok.RequiredArgsConstructor;

/**
 * @author Tamako
 * @since 2025/2/11 14:41
 */
@RequiredArgsConstructor
public class AliBaseImpl {
    /**
     * 日志
     */
    protected static final Log log = LogFactory.get();

    /**
     * 阿里云OSS配置
     */
    protected final AliProperties aliProperties;

}
