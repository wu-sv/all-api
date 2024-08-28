package com.tamako.allapi.wechat.model.miniapp.vo.getphonenumbervo;


import lombok.Data;

/**
 * 数据水印
 *
 * @author Tamako
 */
@Data
public class WaterMark {
    /**
     * 用户获取手机号操作的时间戳
     */
    private Long timestamp;
    /**
     * 小程序appid
     */
    private String appid;
}
