/**
 * 微信手机号登录vo包
 */
package com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo;


import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 手机号登录信息返回值
 *
 * @author Tamako
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetPhoneNumberVo extends ResponseVo {
    /**
     * 用户手机号信息
     */
    private PhoneInfo phoneInfo;
}
