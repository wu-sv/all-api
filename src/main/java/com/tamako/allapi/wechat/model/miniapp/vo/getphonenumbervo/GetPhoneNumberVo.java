package com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo;


import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
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
