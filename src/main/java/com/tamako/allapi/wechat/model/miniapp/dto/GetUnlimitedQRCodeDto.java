package com.tamako.allapi.wechat.model.miniapp.dto;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 */
@Data
public class GetUnlimitedQRCodeDto {
    /**
     * 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符
     * 其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlEncode 处理，请使用其他编码方式）
     */
    @NotNull
    private String scene;
    /**
     * 默认是主页，页面 page，例如 pages/index/index，根路径前不要填加 /，
     * 不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面。
     * scancode_time为系统保留参数，不允许配置
     */
    private String page;
    /**
     * 默认是true，检查page 是否存在，为 true 时 page 必须是已经发布的小程序存在的页面（否则报错）；
     * 为 false 时允许小程序未发布或者 page 不存在， 但page 有数量上限（60000个）请勿滥用。
     */
    @Alias("check_path")
    private Boolean checkPath;
    /**
     * 要打开的小程序版本。正式版为 "release"，体验版为 "trial"，开发版为 "develop"。默认是正式版。
     */
    @Alias("env_version")
    private String envVersion;
    /**
     * 默认430，二维码的宽度，单位 px，最小 280px，最大 1280px
     */
    private Integer width;
    /**
     * 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
     */
    @Alias("auto_color")
    private Boolean autoColor;
    /**
     * 默认是{"r":0,"g":0,"b":0} 。auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     */
    @Alias("line_color")
    private LineColor lineColor;

    /**
     * 默认是false，是否需要透明底色，为 true 时，生成透明底色的小程序
     */
    @Alias("is_hyaline")
    private Boolean isHyaline;

    /**
     * 默认是{"r":0,"g":0,"b":0} 。auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     */
    @Data
    public static class LineColor {
        /**
         * 颜色的 RGB 值，如 {"r":0,"g":0,"b":0}
         */
        private String r;
        /**
         * 颜色的 RGB 值，如 {"r":0,"g":0,"b":0}
         */
        private String g;
        /**
         * 颜色的 RGB 值，如 {"r":0,"g":0,"b":0}
         */
        private String b;
    }
}
