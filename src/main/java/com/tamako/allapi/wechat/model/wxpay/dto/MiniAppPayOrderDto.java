package com.tamako.allapi.wechat.model.wxpay.dto;


import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @data 2024/8/22 09:43
 */
@Data
public class MiniAppPayOrderDto {
    /**
     * 【商品描述】 商品描述
     */
    @NotNull
    private String description;
    /**
     * 【商户订单号】 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
     */
    @NotNull
    private String outTradeNo;

    /**
     * 【交易结束时间】 订单失效时间，遵循rfc3339标准格式，
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，yyyy-MM-DD表示年月日，
     * T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，
     * TIMEZONE表示时区（+08:00表示东八区时间，领先UTC8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日13点29分35秒。
     */
    private DateTime timeExpire;

    /**
     * 【附加数据】 附加数据，在查询API和支付通知中原样返回，
     * 可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。
     */
    private String attach;

    /**
     * 【总金额】 订单总金额，单位为分。
     */
    @NotNull
    private Integer amountTotal;

    /**
     * 【用户标识】 用户在普通商户AppID下的唯一标识。 下单前需获取到用户的OpenID
     */
    @NotNull
    private String payerOpenid;


}
