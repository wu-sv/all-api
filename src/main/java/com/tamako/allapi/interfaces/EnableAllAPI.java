package com.tamako.allapi.interfaces;


import com.tamako.allapi.api.impl.WeChatPayImpl;
import com.tamako.allapi.api.impl.WechatMiniAppImpl;
import com.tamako.allapi.wechat.model.WechatProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Tamako
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigurationProperties(WechatProperties.class)
@Import({WechatMiniAppImpl.class, WeChatPayImpl.class})
public @interface EnableAllAPI {
}
