package com.tamako.allapi.api;


import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.tamako.allapi.ali.model.bailian.dto.ChatDto;
import io.reactivex.functions.Consumer;

/**
 * 阿里百炼大模型API
 *
 * @author Tamako
 * @since 2025/2/11 13:40
 */
public interface AliBaiLianApi {
    /**
     * 流式聊天
     *
     * @param chatDto  聊天参数
     * @param consumer 处理每一次结果方法
     */
    void chatWithStream(ChatDto chatDto, Consumer<? super GenerationResult> consumer);

    /**
     * 文本聊天（一次性输出）
     *
     * @param chatDto 聊天参数
     * @return 聊天结果
     */
    GenerationResult chatWithMsg(ChatDto chatDto);
}
