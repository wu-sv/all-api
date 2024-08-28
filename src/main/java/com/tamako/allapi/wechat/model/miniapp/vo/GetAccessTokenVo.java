/**
 * 微信小程序vo包
 */
package com.tamako.allapi.wechat.model.miniapp.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * 获取小程序全局唯一后台接口调用凭据（access_token）返回对象
 *
 * @author Tamako
 */
@Data
public class GetAccessTokenVo {
    /**
     * 获取到的凭证
     */
    @Alias("access_token")
    private String accessToken;
    /**
     * 凭证有效时间，单位：秒
     * 7200秒后需要重新获取
     * 过期时间是微信服务器返回的，开发者不需要自己计算
     */
    @Alias("expires_in")
    private Integer expiresIn;
}
