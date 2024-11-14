package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 音视频流录制黑名单
 * @author Tamako
 * @since 2024/11/14 16:04
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcludeStreams {
    /**
     * 音视频流列表，由Stream组成，可以为空。为空时，表示订阅房间内所有流。
     * 在一个 StreamList 中，Stream.Index 不能重复。
     */
    private List<Stream> streamList;
}
