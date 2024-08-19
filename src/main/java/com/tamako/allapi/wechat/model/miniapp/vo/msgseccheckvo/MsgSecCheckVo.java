package com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.util.List;

/**
 * @author Tamako
 * @data 2024/8/19 15:21
 */
@Data
public class MsgSecCheckVo {
    /**
     * 错误码
     */
    private Integer errCode;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 详细检测结果
     */
    private List<Detail> detail;
    /**
     * 唯一请求标识，标记单次请求
     */
    @Alias("request_id")
    private String traceId;
    /**
     * 综合结果
     */
    private Result result;
}
