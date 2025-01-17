package com.tamako.allapi.api;


import com.tamako.allapi.ali.model.nls.dto.GetFileTransResultDto;
import com.tamako.allapi.ali.model.nls.vo.NlsResult;
import org.jetbrains.annotations.NotNull;

/**
 * 阿里智能语音交互API
 *
 * @author Tamako
 * @since 2025/1/16 15:42
 */
public interface AliNLSApi {
    /**
     * 提交文件转写请求
     *
     * @param dto 请求参数
     * @return 请求结果
     */
    NlsResult submitFileTransRequest(GetFileTransResultDto dto);

    /**
     * 获取文件转写结果
     *
     * @param taskId 任务ID
     * @return 转写结果
     */
    NlsResult getFileTransResult(@NotNull String taskId);
}
