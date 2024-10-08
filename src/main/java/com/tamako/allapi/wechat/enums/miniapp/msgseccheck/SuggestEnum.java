package com.tamako.allapi.wechat.enums.miniapp.msgseccheck;


import lombok.Getter;

/**
 * 建议值枚举
 *
 * @author Tamako
 */
@Getter
public enum SuggestEnum {
    /**
     * 风险数据不建议通过
     */
    risky,
    /**
     * 只用当为pass时，表示检测结果建议通过
     */
    pass,
    /**
     * 需要人工审核
     */
    review
}
