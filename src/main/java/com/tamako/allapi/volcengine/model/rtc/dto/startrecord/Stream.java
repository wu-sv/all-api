package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/14 16:02
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Stream {
    /**
     * 在自定义布局中，使用 Index 对流进行标志。
     * 后续在 Layout.regions.StreamIndex 中，你需要使用 Index 指定对应流的布局设置。
     */
    private Integer index;
    /**
     * 用户 ID，表示这个流所属的用户。
     */
    private String userId;
    /**
     * 流的类型。支持取值及含义如下：
     * <p>
     * 0：普通音视频流，
     * 1：屏幕流。
     * <p>
     * 默认值为0。
     */
    private Integer streamType;
}
