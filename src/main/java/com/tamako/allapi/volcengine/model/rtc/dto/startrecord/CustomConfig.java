package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 第三方存储平台设置
 *
 * @author Tamako
 * @since 2024/11/14 16:34
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomConfig {
    /**
     * 第三方云存储平台。支持取值及含义如下：
     * <p>
     * 0：Amazon S3
     * 1：阿里云 OSS
     * 2：华为云 OBS
     * 3：腾讯云 COS
     * 4：七牛云 Kodo。
     * <p>
     * 默认值为 0。
     */
    private Integer vendor;
    /**
     * 不同存储平台支持的 Region 不同，具体参看 Region对照表
     * 默认值为0。
     */
    private Integer region;
    /**
     * 存储桶的名称。
     */
    private String bucket;
    /**
     * 第三方存储平台账号的密钥。需确保此账号对存储桶有写权限。不建议开启读权限
     */
    private String accessKey;
    /**
     * 第三方存储平台账号的密钥
     */
    private String secretKey;
}
