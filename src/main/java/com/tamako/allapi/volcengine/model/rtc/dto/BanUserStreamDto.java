package com.tamako.allapi.volcengine.model.rtc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * 封禁音视频流请求参数
 *
 * @author Tamako
 * @since 2024/11/14 09:36
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BanUserStreamDto {
    /**
     * 房间的 ID
     */
    @NotNull
    private String roomId;
    /**
     * 需要封禁音/视频流的用户的 ID
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
    /**
     * 封禁时长，取值范围为{0,[60,259200]}，单位为秒。
     * 若传入值为空或 0 表示封禁音视频流至主动调用unbanroomuser 接口解封。
     * 若传入值大于 0，且小于10，自动修改为10。
     * 若传入值大于259200，自动修改为259200。
     */
    private Long forbiddenInterval;
}
