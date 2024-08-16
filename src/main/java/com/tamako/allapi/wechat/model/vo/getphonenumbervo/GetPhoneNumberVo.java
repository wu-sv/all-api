package com.tamako.allapi.wechat.model.vo.getphonenumbervo;


import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/16 15:07
 */
@Data
public class GetPhoneNumberVo {
    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 用户手机号信息
     */
    private PhoneInfo phoneInfo;
}
