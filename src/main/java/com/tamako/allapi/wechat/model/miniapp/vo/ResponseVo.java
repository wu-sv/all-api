package com.tamako.allapi.wechat.model.miniapp.vo;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 常用的微信小程序返回结果的VO
 *
 * @author Tamako
 */
@Data
@Accessors(chain = true)
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
