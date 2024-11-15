package com.tamako.allapi.volcengine.model.rtc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/15 11:20
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetRecordTaskDto {
    /**
     * 房间的 ID，是房间的唯一标志
     */
    private String roomId;
    /**
     * 要查询的云端录制任务 ID。
     */
    private String taskId;
}
