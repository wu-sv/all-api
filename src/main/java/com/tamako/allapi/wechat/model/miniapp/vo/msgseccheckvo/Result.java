package com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo;
import com.tamako.allapi.wechat.enumerations.msgseccheck.LabelEnum;
import com.tamako.allapi.wechat.enumerations.msgseccheck.SuggestEnum;

import lombok.Data;

/**
 * @author Tamako
 * @data 2024/8/19 16:08
 */
@Data
public class Result {
    /**
     * 建议
     * @see SuggestEnum
     */
    private String suggest;
    /**
     * 命中标签枚举值
     * @see LabelEnum
     */
    private Integer label;
}
