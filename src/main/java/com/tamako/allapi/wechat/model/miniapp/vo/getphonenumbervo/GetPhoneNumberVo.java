/**
 * 微信手机号登录vo包
 */
package com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo;


import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手机号登录信息返回值
 *
 * @author Tamako
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class GetPhoneNumberVo extends ResponseVo {
    /**
     * 用户手机号信息
     */
    private PhoneInfo phoneInfo;
}
