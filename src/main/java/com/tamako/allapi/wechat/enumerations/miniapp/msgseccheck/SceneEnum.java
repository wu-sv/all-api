package com.tamako.allapi.wechat.enumerations.miniapp.msgseccheck;


import lombok.Getter;

/**
 * @author Tamako
 */
@Getter
public enum SceneEnum {
    /**
     * 资料
     */
    INFORMATION(1, "资料"),
    /**
     * 评论
     */
    COMMENTS(2, "评论"),
    /**
     * 论坛
     */
    FORUM(3, "论坛"),
    /**
     * 社交日志
     */
    SOCIAL_LOGS(4, "社交日志");
    /**
     * 场景值
     */
    private final Integer code;
    /**
     * 场景描述
     */
    private final String desc;

    /**
     * 构造方法
     * @param code 场景值
     * @param desc 场景描述
     */
    SceneEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
