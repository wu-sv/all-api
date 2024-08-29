/**
 * 注解包
 */
package com.tamako.allapi.interfaces;


import com.tamako.allapi.ali.model.AliProperties;
import com.tamako.allapi.api.impl.AliOSSImpl;
import com.tamako.allapi.api.impl.AliSMSImpl;
import com.tamako.allapi.api.impl.WeChatPayImpl;
import com.tamako.allapi.api.impl.WechatMiniAppImpl;
import com.tamako.allapi.wechat.model.WechatProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 注解类，用于启用所有API
 *
 * @author Tamako
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigurationProperties({WechatProperties.class, AliProperties.class})
@Import({AliOSSImpl.class, AliSMSImpl.class, WechatMiniAppImpl.class, WeChatPayImpl.class})
public @interface EnableAllAPI {
}
