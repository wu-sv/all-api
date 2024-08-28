/**
 * 微信文本安全校验vo包
 */
package com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo;

import com.tamako.allapi.wechat.enums.miniapp.msgseccheck.LabelEnum;
import com.tamako.allapi.wechat.enums.miniapp.msgseccheck.SuggestEnum;
import lombok.Data;

/**
 * @author Tamako
 *
 * 详细检测结果
 */
@Data
public class Detail {
    /**
     * 策略类型
     */
    private String strategy;
    /**
     * 错误码，仅当该值为0时，该项结果有效
     */
    private Integer errcode;
    /**
     * 建议
     *
     * @see SuggestEnum
     */
    private String suggest;
    /**
     * 命中标签枚举值
     *
     * @see LabelEnum
     */
    private Integer label;
    /**
     * 命中的自定义关键词
     */
    private String keyword;
    /**
     * 0-100，代表置信度，越高代表越有可能属于当前返回的标签（label）
     */
    private Integer prob;
}
