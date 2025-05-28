package com.tamako.allapi.volcengine.model.rtc.vo.getrecordtask;


import com.tamako.allapi.volcengine.model.rtc.vo.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 查询录制任务状态返回结果
 *
 * @author Tamako
 * @since 2024/11/15 11:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GetRecordTaskResult extends BaseResult {
    /**
     * 录制任务信息
     */
    private RecordTask recordTask;
}
