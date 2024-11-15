package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 点播平台设置
 *
 * @author Tamako
 * @since 2024/11/14 16:32
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class VodConfig {
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
     * 点播空间名称。
     */
    private String space;
    /**
     * 上传到视频点播平台时, 文件的存储类型。支持取值及含义如下：：
     * <p>
     * 1：标准存储。
     * 2：归档存储。
     * 3：低频存储。
     * <p>
     * 默认值为 1。
     * 关于存储类型的详细说明，参看媒资存储存储类型
     */
    private Integer storageClass;
    /**
     * 上传到视频点播平台时, 是否需要根据文件后缀自动设置 FileExtension。关于 FileExtension 的详细说明，参看 FileExtension。
     * <p>
     * true：需要；
     * false：不需要。
     * <p>
     * 默认值为 false。
     */
    private Boolean autoSetFileExtension;
}
