package com.tamako.allapi.api;


import com.tamako.allapi.ali.model.nls.dto.GetFileTransResultDto;
import com.tamako.allapi.ali.model.nls.vo.BaseResult;

/**
 * 阿里智能语音交互API
 *
 * @author Tamako
 * @since 2025/1/16 15:42
 */
public interface AliNLSApi {
    BaseResult getFileTransResult(GetFileTransResultDto dto);
}
