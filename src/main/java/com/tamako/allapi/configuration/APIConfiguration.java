package com.tamako.allapi.configuration;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.configuration.properties.AliProperties;
import com.tamako.allapi.configuration.properties.AllApiProperties;
import com.tamako.allapi.configuration.properties.VolcEngineProperties;
import com.tamako.allapi.configuration.properties.WechatProperties;
import com.tamako.allapi.utils.ResourceUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Tamako
 * @since 2024/8/30 14:44
 */
@Configuration
@EnableConfigurationProperties({
        WechatProperties.class,
        AliProperties.class,
        VolcEngineProperties.class,
        AllApiProperties.class
})
@Import({
        WechatConfiguration.class,
        AliConfiguration.class,
        VolcEngineConfiguration.class
})
public class APIConfiguration {
    /**
     * 日志
     */
    protected static final Log log = LogFactory.get();

    /**
     * 构造方法
     */
    public APIConfiguration() {
        String version = ResourceUtil.getVersion();
        log.info("Initializing [Tamako ALL-API/{}]", version);
    }
}
