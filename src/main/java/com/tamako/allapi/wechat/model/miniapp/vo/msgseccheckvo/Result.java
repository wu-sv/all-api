package com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo;
import com.tamako.allapi.wechat.enumerations.msgseccheck.MsgSecCheckLabelEnum;
import com.tamako.allapi.wechat.enumerations.msgseccheck.MsgSecCheckSuggestEnum;

import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/19 16:08
 */
@Data
public class Result {
    /**
     * 建议
     * @see MsgSecCheckSuggestEnum
     */
    private String suggest;
    /**
     * 命中标签枚举值
     * @see MsgSecCheckLabelEnum
     */
    private Integer label;
}
