package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 录制文件的存储平台配置。
 *
 * @author Tamako
 * @since 2024/11/14 16:30
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageConfig {
    /**
     * 存储平台类型
     * <p>
     * 0：火山引擎对象存储 TOS
     * 1: 火山引擎视频点播 VOD
     * 2: 支持 S3 协议的第三方存储平台
     * <p>
     * 默认值为 0。
     */
    private Integer type;
    /**
     * Tos 平台设置。当 Type = 0 时，需正确设置 TosConfig 的值，否则请求会报错
     */
    private TosConfig tosConfig;
    /**
     * 点播平台设置。当 Type = 1 时，需正确设置 VodConfig 的值，否则请求会报错
     */
    private VodConfig vodConfig;
    /**
     * 第三方存储平台设置。当 Type = 2时，需正确设置 CustomConfig 的值，否则请求会报错
     */
    private CustomConfig customConfig;
}
