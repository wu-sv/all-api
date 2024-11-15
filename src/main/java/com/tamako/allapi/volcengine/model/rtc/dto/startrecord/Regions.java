package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 每一路视频流进行画面布局设置
 *
 * @author Tamako
 * @since 2024/11/14 16:15
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Regions {
    /**
     * 流的标识。这个标志应和 TargetStreams.StreamList.Stream.Index 对应。
     */
    private Integer streamIndex;
    /**
     * 视频流对应区域左上角的横坐标相对整体画面左上角原点的横向位移，取值的范围为 [0.0, Canvas.Width)。
     * 默认值为 0。若传入该参数，服务端会对该参数进行校验，若不合法会返回错误码 InvalidParameter。
     * 视频流对应区域左上角的实际坐标是通过画面尺寸和相对位置比例相乘，并四舍五入取整得到的。
     * 假如，画布尺寸为1920, 视频流对应区域左上角的横坐标相对整体画面的比例为 0.33，
     * 那么该画布左上角的横坐标为 634（1920*0.33=633.6，四舍五入取整）。
     */
    private Integer locationX;
    /**
     * 视频流对应区域左上角的横坐标相对整体画面左上角原点的纵向位移，取值的范围为 [0.0, Canvas.Height)。
     * 默认值为 0。若传入该参数，服务端会对该参数进行校验，若不合法会返回错误码 InvalidParameter。
     */
    private Integer locationY;
    /**
     * 视频流对应区域宽度的像素绝对值，取值的范围为 (0.0, Canvas.Width]。
     */
    private Integer width;
    /**
     * 视频流对应区域高度的像素绝对值，取值的范围为 (0.0, Canvas.Height]。
     */
    private Integer height;
    /**
     * 当多个流的画面有重叠时，使用此参数设置指定画面的图层顺序。取值范围为 [0, 100]，默认值为 0。
     * 0 表示该区域图像位于最下层，100 表示该区域图像位于最上层。
     */
    private Integer zOrder;
    /**
     * 画面的透明度，取值范围为 (0.0, 1.0]，默认值为 1.0。
     * 0.0 表示完全透明，1.0 表示完全不透明。
     */
    private Float alpha;
    /**
     * 画面的渲染模式。支持取值及含义如下：
     * <p>
     * 0 ：按照指定的宽高直接缩放。如果原始画面宽高比与指定的宽高比不同，就会导致画面变形
     * 1 ：按照显示区域的长宽比裁减视频，然后等比拉伸或缩小视频，占满显示区域。
     * 2 ：按照原始画面的宽高比缩放视频，在显示区域居中显示。如果原始画面宽高比与指定的宽高比不同，就会导致画面有空缺，空缺区域为填充的背景色值。
     * 3 ：按照指定的宽高直接缩放。如果原始画面宽高比与指定的宽高比不同，就会导致画面变形。
     * <p>
     * 默认值为 0。
     * 目前 0 和 3 均为按照指定的宽高直接缩放，但我们推荐你使用 3 以便与客户端实现相同逻辑。
     */
    private Integer renderMode;
    /**
     * 源流剪切功能，可以在源视频帧渲染之前进行裁剪，即预处理一次再渲染。
     */
    private SourceCrop sourceCrop;
    /**
     * 补位图片的 url。长度不超过 1024 个字符串。
     * <p>
     * 在 Region.StreamIndex 对应的视频流没有发布，或被暂停采集时，AlternateImage 对应的图片会填充 Region 对应的画布空间。当视频流被采集并发布时，AlternateImage 不造成任何影响。
     * 可以传入的图片的格式包括：JPG, JPEG, PNG。
     * 当图片和画布尺寸不一致时，图片根据 RenderMode 的设置，在画布中进行渲染。
     */
    private String alternateImage;
    /**
     * 该路流对应的用户是否开启空间音频效果。
     * <p>
     * true：开启空间音频效果。
     * false：关闭空间音频效果。
     * <p>
     * 默认值为 true
     */
    private Boolean applySpatialAudio;
    /**
     * 空间音频下，房间内指定用户所在位置的三维坐标，默认值为[0,0,0]。数组长度为3，三个值依次对应X,Y,Z，每个值的取值范围为 [-100,100]。
     */
    private Integer[] spatialPosition;
    /**
     * 转推直播下边框圆角半径与画布宽度的比例值，取值范围为 [0,1]。默认值为 0，表示没有圆角效果。
     * 圆角半径的像素位不能超过 Region 宽高最小值的一半，否则不会出现圆角效果。
     */
    private Float cornerRadius;
    /**
     * 该路流参与混流的媒体类型。支持取值及含义如下：
     * <p>
     * 0：音视频
     * 1：纯音频
     * 2：纯视频
     * <p>
     * 默认值为 0。
     * 例如该路流为音视频流，MediaType设为1，则只混入音频内容。
     */
    private Integer mediaType;
    /**
     * 画面的渲染模式。支持取值及含义如下：
     * <p>
     * 0：按照用户原始视频帧比例进行缩放
     * 1：保持图片原有比例
     * <p>
     * 默认值为 0。
     */
    private Integer alternateImageFillMode;
}
