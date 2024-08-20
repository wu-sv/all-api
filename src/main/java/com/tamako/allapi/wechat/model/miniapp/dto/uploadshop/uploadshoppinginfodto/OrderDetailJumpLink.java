package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @data 2024/8/20 11:15
 */
@Data
public class OrderDetailJumpLink {
    /**
     * 链接地址（链接类型为H5时必填）
     * 示例值: https://www.weixin.qq.com/wxpay/pay.php 字符字节限制: [1, 1024] 匹配正则表达式: ^https?😕/([^\s/?#[]@]+@)?([^\s/?#@:]+)(?::\d{2,5})?([^[]]*)$
     */
    private String url;
    /**
     * 小程序appid（链接类型为MINIAPP时必填）
     * 示例值: wxd678efh567hg6787 字符字节限制: [1, 32]
     */
    private String appid;
    /**
     * 小程序path（链接类型为MINIAPP时必填）
     * 示例值: /path/index/index 字符字节限制: [1, 512]
     */
    private String path;
    /**
     * 链接类型枚举值：1、URL；2、MINI_PROGRAM
     * 示例值: 1
     */
    @NotNull
    private Integer type;
}
