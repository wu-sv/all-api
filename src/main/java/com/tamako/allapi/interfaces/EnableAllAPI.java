/**
 * 注解包
 */
package com.tamako.allapi.interfaces;


import com.tamako.allapi.configuration.APIConfiguration;
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
@Import(APIConfiguration.class)
public @interface EnableAllAPI {
}
