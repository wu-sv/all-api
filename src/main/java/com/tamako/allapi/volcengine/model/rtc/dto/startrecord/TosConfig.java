package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/14 16:31
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TosConfig {
    /**
     * 火山引擎平台账号 ID，例如：200000000。
     * <p>
     * 火山引擎平台账号 ID 查看路径参看查看和管理账号信息。
     * <p>
     * 此账号 ID 为火山引擎主账号 ID。
     * <p>
     * 若你调用 OpenAPI 鉴权过程中使用的 AK、SK 为子用户 AK、SK，账号 ID 也必须为火山引擎主账号 ID，不能使用子用户账号 ID。
     */
    private String accountId;
    /**
     * 不同存储平台支持的 Region 不同，具体参看 Region对照表
     * 默认值为0。
     */
    private Integer region;
    /**
     * 存储桶的名称。
     */
    private String bucket;
}
