package com.tamako.allapi.wechat.model.miniapp.vo.msgseccheckvo;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Tamako
 *
 * 微信文本安全校验vo
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MsgSecCheckVo extends ResponseVo {
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
