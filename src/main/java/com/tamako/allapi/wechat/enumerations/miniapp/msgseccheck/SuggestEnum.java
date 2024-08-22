package com.tamako.allapi.wechat.enumerations.miniapp.msgseccheck;


import lombok.Getter;

/**
 * @author Tamako
 * @data 2024/8/19 16:36
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
