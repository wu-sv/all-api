package com.tamako.allapi.volcengine.model.rtc.vo.getrecordtask;


import com.tamako.allapi.volcengine.model.rtc.dto.startrecord.Stream;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 录制生成的文件列表
 *
 * @author Tamako
 * @since 2024/11/15 11:31
 */
@Accessors(chain = true)
@Data
public class RecordFile {
    /**
     * 文件在火山引擎视频点播 VOD 平台的唯一标识。
     * 你可以根据 vid 在点播平台上找到对应的文件。
     * 仅在你选择配置存储到 Vod 平台时，此参数有效。
     */
    private String vid;
    /**
     * 文件在对象存储平台中的完整路径，如abc/efg/123.mp4。
     * 仅在你选择配置存储到对象存储平台时，此参数有效。
     */
    private String objectKey;
    /**
     * 文件时长，单位为毫秒。
     */
    private Long duration;
    /**
     * 文件大小，单位为字节。
     */
    private Long size;
    /**
     * 当前录制文件创建的时间，为 Unix 时间戳，单位为毫秒。
     */
    private Long startTime;
    /**
     * 录制文件中包含流的列表。
     */
    private List<Stream> streamList;
    /**
     * 视频录制编码协议
     */
    private String videoCodec;
    /**
     * 音频录制编码器
     */
    private String audioCodec;
    /**
     * 录制视频宽度，单位为像素。
     */
    private Integer videoWidth;
    /**
     * 录制视频高度，单位为像素。
     */
    private Integer videoHeight;
}
