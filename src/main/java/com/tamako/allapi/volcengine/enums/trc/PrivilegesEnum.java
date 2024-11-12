package com.tamako.allapi.volcengine.enums.trc;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TRC privileges
 *
 * @author Tamako
 * @since 2024/11/12 15:51
 */
@Getter
@AllArgsConstructor
public enum PrivilegesEnum {
    PRIV_PUBLISH_STREAM(0),

    PRIV_PUBLISH_AUDIO_STREAM(1),
    PRIV_PUBLISH_VIDEO_STREAM(2),
    PRIV_PUBLISH_DATA_STREAM(3),

    PRIV_SUBSCRIBE_STREAM(4);

    public final short intValue;

    PrivilegesEnum(int value) {
        intValue = (short) value;
    }
}
