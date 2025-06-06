package com.tamako.allapi.wechat.model.miniapp.dto;


import com.tamako.allapi.wechat.enums.miniapp.msgseccheck.SceneEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * 用于校验文本安全的请求参数
 *
 * @author Tamako
 */
@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class MsgSecCheckDto {

    /**
     * 需检测的文本内容，文本字数的上限为2500字，需使用UTF-8编码
     */
    @NotNull
    private String content;

    /**
     * 接口版本号，2.0版本为固定值2
     */
    @NotNull
    private Integer version;

    /**
     * 场景枚举值（1 资料；2 评论；3 论坛；4 社交日志）
     *
     * @see SceneEnum
     */
    @NotNull
    private Integer scene;

    /**
     * 用户的openid（用户需在近两小时访问过小程序）
     */
    @NotNull
    private String openid;

    /**
     * 文本标题，需使用UTF-8编码
     */
    private String title;

    /**
     * 用户昵称，需使用UTF-8编码
     */
    private String nickname;

    /**
     * 个性签名，该参数仅在资料类场景有效(scene=1)，需使用UTF-8编码
     */
    private String signature;
}
