package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 布局参数
 *
 * @author Tamako
 * @since 2024/11/14 16:08
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Layout {
    /**
     * 布局模式。支持取值及含义如下：
     * <p>
     * 0：自适应布局。
     * 1：垂直布局
     * 2 ：自定义布局。
     * 3 ：并排布局
     * <p>
     * 默认值为0
     */
    private Integer layoutMode;
    /**
     * 在垂直布局模式下生效，指定主画面流的属性。垂直布局时，此参数必填。
     */
    private MainVideoStream mainVideoStream;
    /**
     * 使用自定义布局模式时，使用此参数进行具体设置。
     */
    private CustomLayout customLayout;
}
