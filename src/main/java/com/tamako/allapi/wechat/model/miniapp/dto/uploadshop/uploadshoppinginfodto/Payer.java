package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto;


import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/20 11:18
 */

@Data
public class Payer {
    /**
     * 用户标识，用户在商户appid下的唯一标识。 下单前需获取到用户的Openid 示例值: oUpF8uMuAJO_M2pxb1Q9zNjWeS6o 字符字节限制: [1, 128]
     */
    private String openid;
}
