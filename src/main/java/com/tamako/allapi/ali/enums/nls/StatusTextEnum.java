package com.tamako.allapi.ali.enums.nls;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tamako
 * @since 2025/1/16 17:30
 */
@Getter
@AllArgsConstructor
public enum StatusTextEnum {
    SUCCESS("SUCCESS"),
    QUEUEING("QUEUEING"),
    RUNNING("RUNNING");

    private final String text;
}
