/**
 *  微信文本安全检测枚举包
 */
package com.tamako.allapi.wechat.enums.miniapp.msgseccheck;


import lombok.Getter;

/**
 * @author Tamako
 *
 * 命中标签枚举值
 */
@Getter
public enum LabelEnum {
    /**
     * 正常
     */
    NORMAL(100, "正常"),
    /**
     * 广告
     */
    ADVERTISEMENT(10001, "广告"),
    /**
     * 时政
     */
    CURRENT_POLITICS(20001, "时政"),
    /**
     * 色情
     */
    PORNOGRAPHY(20002, "色情"),
    /**
     * 辱骂
     */
    ABUSIVE(20002, "辱骂"),
    /**
     * 违法犯罪
     */
    ILLEGAL_CRIME(20006, "违法犯罪"),
    /**
     * 欺诈
     */
    FRAUD(20008, "欺诈"),
    /**
     * 低俗
     */
    VULGAR(20012, "低俗"),
    /**
     * 版权
     */
    COPYRIGHT(20013, "版权"),
    /**
     * 其他
     */
    OTHER(21000, "其他");

    /**
     * 标签值
     */
    private final Integer code;
    /**
     * 标签描述
     */
    private final String msg;

    /**
     * 构造方法
     * @param code code
     * @param msg msg
     */
    LabelEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
