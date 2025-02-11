package com.tamako.allapi.api.impl.base;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.configuration.properties.WechatProperties;
import lombok.RequiredArgsConstructor;

/**
 * @author Tamako
 * @since 2025/2/11 14:56
 */
@RequiredArgsConstructor
public class WeChatBaseImpl {
    /**
     * 日志
     */
    protected static final Log log = LogFactory.get();
    /**
     * 微信配置
     */

    protected final WechatProperties wechatProperties;
}
