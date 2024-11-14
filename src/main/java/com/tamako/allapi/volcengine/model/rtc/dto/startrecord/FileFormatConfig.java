package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 录制文件的格式设置
 *
 * @author Tamako
 * @since 2024/11/14 16:24
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileFormatConfig {
    /**
     * 存储文件格式。支持取值包括：MP4、HLS、FLV、MP3、 AAC、M4A。可同时选择多个存储文件格式，最终生成所选存储格式对应的多个文件。
     * <p>
     * MP3、AAC和M4A仅在编码纯音频时有效。
     */
    private String[] fileFormat;
}
