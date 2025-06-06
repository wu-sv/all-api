package com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo;

import com.tamako.allapi.wechat.enums.miniapp.msgseccheck.LabelEnum;
import com.tamako.allapi.wechat.enums.miniapp.msgseccheck.SuggestEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 综合结果
 *
 * @author Tamako
 */
@Data
@Accessors(chain = true)
public class Result {
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
}
