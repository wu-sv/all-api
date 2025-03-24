package com.tamako.allapi.configuration;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author Tamako
 * @since 2025/3/24 16:59
 */
@AutoConfiguration
@Import({APIConfiguration.class})
public class AllApiAutoConfiguration {
}
