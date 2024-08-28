package com.tamako.allapi.wechat.model.miniapp.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tamako
 *
 * 微信小程序登录凭证校验返回结果
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JsCode2SessionVo extends ResponseVo{
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
     * 用户唯一标识(最需要存储的)
     */
    private String openid;

}
