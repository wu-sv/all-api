package com.tamako.allapi.configuration.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Tamako
 * @since 2025/3/25 10:59
 */
@Data
@ConfigurationProperties(prefix = "all-api")
@Component
public class AllApiProperties {

}
