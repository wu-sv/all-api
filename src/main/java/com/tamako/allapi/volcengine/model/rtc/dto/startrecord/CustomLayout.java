package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 自定义布局模式
 *
 * @author Tamako
 * @since 2024/11/14 16:12
 */
@Data
@Accessors(chain = true)

@AllArgsConstructor
@NoArgsConstructor
public class CustomLayout {
    /**
     * 整体屏幕（画布）的宽高以及背景色。
     */
    private Canvas canvas;
    /**
     * 在自定义布局模式下，你可以使用 Regions 对每一路视频流进行画面布局设置。
     * 其中，每个 Region 对一路视频流进行画面布局设置。
     * <p>
     * 自定义布局模式下，对于 StreamList 中的每个 Stream，Regions 中都需要给出对应的布局信息，否则会返回请求不合法的错误。
     * 即 Regions.Region.StreamIndex 要与 TargetStreams.StreamList.Stream.Index 的值一一对应，
     * 否则自定义布局设置失败，返回 InvalidParameter 错误码。
     * <p>
     * 当传入的必填参数值不合法时，返回错误码 InvalidParameter 。
     */
    private Regions regions;
}
