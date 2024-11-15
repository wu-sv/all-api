package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 录制文件的命名设置
 *
 * @author Tamako
 * @since 2024/11/14 16:28
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileNameConfig {
    /**
     * 指定录制文件名的前缀，对TosConfig和CustomConfig生效。
     * 在 TOS 以及支持 S3 的第三方存储平台上，可以实现指定文件夹的功能。
     * 如：Prefix = ["directory1","directory2"]，将在录制文件名前加上前缀 "directory1/directory2/"。
     * 前缀长度最大值为 128 个字符。仅支持大小写字母、数字。
     */
    private String[] prefix;
    /**
     * 自定义录制文件名模式，具体参看自定义录制文件名。
     * 如果你设置了 Pattern，需自行保证最终文件名的唯一性，否则在 TOS 或第三方存储平台上同名文件将被覆盖;
     * 你也可以给对应 bucket 开启版本控制，允许文件名重复，防止被覆盖的情况发生。
     */
    private String pattern;
}
