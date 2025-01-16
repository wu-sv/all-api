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
    FILE_TRANS("nls-filetrans");

    private final String name;
}
