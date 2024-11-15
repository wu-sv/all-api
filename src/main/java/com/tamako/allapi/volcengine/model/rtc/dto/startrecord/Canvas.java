package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 整体屏幕（画布）的宽高以及背景色
 *
 * @author Tamako
 * @since 2024/11/14 16:12
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Canvas {
    /**
     * 整体屏幕（画布）的宽度，取值范围为 [2, 1920]，必须是偶数，单位为像素，默认值为 640。
     */
    private Integer width;
    /**
     * 整体屏幕（画布）的高度，取值范围为 [2, 1920]，必须是偶数，单位为像素，默认值为 480。
     */
    private Integer height;
    /**
     * 整体屏幕（画布）的背景色, 范围为 #000000 ~ #ffffff (大小写均可)，格式为 #RGB(16进制)，默认值为 #000000（黑色）。
     */
    private String background;
    /**
     * 背景图片的 URL。长度最大为 1024 byte。可以传入的图片的格式包括：JPG, JPEG, PNG。
     * 如果背景图片的宽高和整体屏幕的宽高不一致，背景图片会缩放到铺满屏幕。
     * 如果你设置了背景图片，背景图片会覆盖背景色。
     */
    private String backgroundImage;
}
