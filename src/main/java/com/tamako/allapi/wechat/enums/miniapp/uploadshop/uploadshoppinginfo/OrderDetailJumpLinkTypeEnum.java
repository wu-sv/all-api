package com.tamako.allapi.wechat.enums.miniapp.uploadshop.uploadshoppinginfo;


import lombok.Getter;

/**
 * 订单详情跳转链接类型枚举
 *
 * @author Tamako
 * @since 2024/8/29 11:12
 */
@Getter
public enum OrderDetailJumpLinkTypeEnum {
    /**
     * URL 链接
     */
    URL(1),
    /**
     * 小程序链接
     */
    MINI_PROGRAM(2);
    /**
     * code
     */
    private final Integer code;

    /**
     * 构造方法
     * @param code
     */
    OrderDetailJumpLinkTypeEnum(Integer code) {
        this.code = code;
    }
}
