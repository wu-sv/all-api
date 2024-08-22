package com.tamako.allapi.wechat.model.wxpay.vo;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.enumerations.BankCode;
import com.tamako.allapi.wechat.enumerations.wxpay.TradeStateEnum;
import com.tamako.allapi.wechat.enumerations.wxpay.TradeTypeEnum;
import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshoppinginfodto.Payer;
import lombok.Data;

import java.util.List;

/**
 * @author Tamako
 * @data 2024/8/22 11:38
 */
@Data
public class MiniAppPayNotifyVo {
    /**
     * 直连商户申请的公众号或移动应用AppID
     */
    private String appid;
    /**
     * 商户的商户号，由微信支付生成并下发。
     */
    private String mchId;
    /**
     * 商户系统内部订单号，可以是数字、大小写字母_-*的任意组合且在同一个商户号下唯一。
     */
    @Alias("out_trade_no")
    private String outTradeNo;
    /**
     * 微信支付系统生成的订单号。
     */
    @Alias("transaction_id")
    private String transactionId;
    /**
     * 交易类型，枚举值：
     * JSAPI：公众号支付
     * NATIVE：扫码支付
     * App：App支付
     * MICROPAY：付款码支付
     * MWEB：H5支付
     * FACEPAY：刷脸支付
     *
     * @see TradeTypeEnum
     */
    @Alias("trade_type")
    private String tradeType;

    /**
     * 交易状态，枚举值：
     * SUCCESS：支付成功
     * REFUND：转入退款
     * NOTPAY：未支付
     * CLOSED：已关闭
     * REVOKED：已撤销（付款码支付）
     * USERPAYING：用户支付中（付款码支付）
     * PAYERROR：支付失败(其他原因，如银行返回失败)
     *
     * @see TradeStateEnum
     */
    @Alias("trade_state")
    private String tradeState;

    /**
     * 交易状态描述。
     */
    @Alias("trade_state_desc")
    private String tradeStateDesc;

    /**
     * 银行类型，采用字符串类型的银行标识。
     *
     * @see BankCode
     */
    @Alias("bank_type")
    private String bankType;

    /**
     * 附加数据，在查询API和支付通知中原样返回，
     * 可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。
     */
    private String attach;

    /**
     * 支付完成时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，
     * yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，
     * TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     */
    private String successTime;

    /**
     * 支付者信息
     */
    private Payer payer;

    /**
     * 订单金额信息
     */
    private Amount amount;
    /**
     * 支付场景信息描述
     */
    private SceneInfo sceneInfo;

    @Data
    public static class Amount {
        /**
         * 订单总金额，单位为分。
         */
        private Integer total;

        /**
         * 用户支付金额，单位为分。
         */
        @Alias("payer_total")
        private Integer payerTotal;

        /**
         * CNY：人民币，境内商户号仅支持人民币。
         */
        private String currency;

        /**
         * 用户支付币种。
         */
        @Alias("payer_currency")
        private String payerCurrency;
    }

    @Data
    public static class SceneInfo {
        /**
         * 终端设备号（门店号或收银设备ID）
         */
        @Alias("device_id")
        private String deviceId;
    }

}
