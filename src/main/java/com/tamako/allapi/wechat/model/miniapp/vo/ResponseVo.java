package com.tamako.allapi.wechat.model.miniapp.vo;


import lombok.Data;

/**
 * @author Tamako
 *
 * 常用的微信小程序返回结果的VO
 */
@Data
public class ResponseVo {
    /**
     * 错误码
     */
    private Integer errcode;
    /**
     * 错误信息
     */
    private String errmsg;
}
