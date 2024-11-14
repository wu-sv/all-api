package com.tamako.allapi.volcengine.model.rtc.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @since 2024/11/14 14:17
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnbanUserStreamDto {
    /**
     * 房间的 ID，是房间的唯一标志
     */
    @NotNull
    private String roomId;
    /**
     * 需要被解封音/视频流的用户的 ID
     */
    @NotNull
    private String userId;
    /**
     * 是否解封视频流。
     * true：解封视频流。
     * false：封禁视频流。
     * 默认值为 true。
     */
    private Boolean video;
    /**
     * 是否解封音频流。
     * true：解封音频流。
     * false：封禁音频流。
     * 默认值为 true。
     */
    private Boolean audio;
}
