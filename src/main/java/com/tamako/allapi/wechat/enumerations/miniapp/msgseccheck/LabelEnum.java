package com.tamako.allapi.wechat.enumerations.miniapp.msgseccheck;


import lombok.Getter;

/**
 * @author Tamako
 * @data 2024/8/19 16:13
 */
@Getter
public enum LabelEnum {
    NORMAL(100, "正常"),
    ADVERTISEMENT(10001, "广告"),
    CURRENT_POLITICS(20001, "时政"),
    PORNOGRAPHY(20002, "色情"),
    ABUSIVE(20002, "辱骂"),
    ILLEGAL_CRIME(20006, "违法犯罪"),
    FRAUD(20008, "欺诈"),
    VULGAR(20012, "低俗"),
    COPYRIGHT(20013, "版权"),
    OTHER(21000, "其他");

    private final Integer code;
    private final String msg;

    LabelEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
