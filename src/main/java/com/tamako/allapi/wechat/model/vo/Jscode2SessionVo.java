package com.tamako.allapi.wechat.model.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/16 14:19
 */
@Data
public class Jscode2SessionVo {
    /**
     * 会话密钥
     */
    @Alias("session_key")
    private String sessionKey;

    /**
     * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台账号下会返回，详见 UnionID 机制说明。
     */
    @Alias("unionid")
    private String unionId;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 用户唯一标识(最需要存储的)
     */
    private String openid;

    /**
     * 错误码
     */
    private Integer errcode;
}
