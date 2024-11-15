package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 源流剪切功能参数
 *
 * @author Tamako
 * @since 2024/11/14 16:18
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SourceCrop {
    /**
     * 裁剪后得到的视频帧左上角的横坐标相对裁剪前整体画面的比例，取值的范围为 [0.0, 1.0)。默认值为 0.0。
     */
    private Float locationX;
    /**
     * 裁剪后得到的视频帧左上角的纵坐标相对裁剪前整体画面的比例，取值的范围为 [0.0, 1.0)。默认值为 0.0。
     */
    private Float locationY;
    /**
     * 裁剪后得到的视频帧宽度相对裁剪前整体画面宽度的比例，取值范围为 (0.0, 1.0]。默认值为 1.0。
     */
    private Float widthProportion;
    /**
     * 裁剪后得到的视频帧高度相对裁剪前整体画面宽度的比例，取值范围为 (0.0, 1.0]。默认值为 1.0。
     */
    private Float heightProportion;
}
