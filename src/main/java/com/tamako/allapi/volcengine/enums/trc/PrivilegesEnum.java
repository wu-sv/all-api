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
    /**
     * 发布直播流的权限
     */
    PRIV_PUBLISH_STREAM(0),

    //中间这三个不需要使用，因为上面的那个代表这三个的权限
    /**
     * 发布音频流的权限
     */
    PRIV_PUBLISH_AUDIO_STREAM(1),
    /**
     * 发布视频流的权限
     */
    PRIV_PUBLISH_VIDEO_STREAM(2),
    /**
     * 发布数据流的权限
     */
    PRIV_PUBLISH_DATA_STREAM(3),

    /***
     * 订阅直播流的权限
     */
    PRIV_SUBSCRIBE_STREAM(4);

    /**
     * 权限值
     */
    public final short intValue;

    /**
     * 构造函数
     *
     * @param value 权限值
     */
    PrivilegesEnum(int value) {
        intValue = (short) value;
    }
}
