package com.tamako.allapi.wechat.enumerations.msgseccheck;


import lombok.Getter;

/**
 * @author Tamako
 * @data 2024/8/19 15:51
 */
@Getter
public enum SceneEnum {
    INFORMATION(1, "资料"),
    COMMENTS(2, "评论"),
    FORUM(3, "论坛"),
    SOCIAL_LOGS(4, "社交日志");
    private final Integer code;
    private final String desc;

    SceneEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
