package com.tamako.allapi.ali.enums.nls;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * NLS产品类型枚举
 *
 * @author Tamako
 * @since 2025/1/16 17:01
 */
@Getter
@AllArgsConstructor
public enum NLSProductEnum {
    /**
     * 语音识别
     */
    FILE_TRANS("nls-filetrans");

    /**
     * 产品名称
     */
    private final String name;
}
