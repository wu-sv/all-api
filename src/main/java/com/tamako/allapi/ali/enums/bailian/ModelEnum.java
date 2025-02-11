package com.tamako.allapi.ali.enums.bailian;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 百炼大模型枚举
 *
 * @author Tamako
 * @since 2025/2/11 15:11
 */
@Getter
@AllArgsConstructor
public enum ModelEnum {
    /**
     * 通义千问MAX
     */
    QWEN_MAX("qwen-max");
    /**
     * 大模型参数
     */
    private final String value;

    /**
     * 转换为字符串
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        return value;
    }
}
