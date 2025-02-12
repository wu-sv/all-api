package com.tamako.allapi.api.impl;


import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.tamako.allapi.ali.model.bailian.dto.ChatDto;
import com.tamako.allapi.api.AliBaiLianApi;
import com.tamako.allapi.api.impl.base.AliBaseImpl;
import com.tamako.allapi.configuration.properties.AliProperties;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import com.tamako.allapi.utils.ObjUtil;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;


/**
 * 阿里百炼大模型API实现类
 *
 * @author Tamako
 * @since 2025/2/11 13:42
 */
public class AliBaiLianImpl extends AliBaseImpl implements AliBaiLianApi {
    /**
     * 构造方法
     *
     * @param aliProperties 阿里云OSS配置
     */
    public AliBaiLianImpl(AliProperties aliProperties) {
        super(aliProperties);
    }

    /**
     * 流式聊天（流式输出）
     *
     * @param chatDto  聊天参数
     * @param consumer 处理每一次结果方法
     */
    @Override
    public void chatWithStream(ChatDto chatDto, Consumer<? super GenerationResult> consumer) {
        Generation gen = new Generation();
        GenerationParam param = getGenerationParam(chatDto);
        try {
            Flowable<GenerationResult> result = gen.streamCall(param);
            result.blockingForEach(consumer);
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new AllApiException(PlatformEnum.ALI, e.getMessage());
        }
    }

    /**
     * 文本聊天（一次性输出）
     *
     * @param chatDto 聊天参数
     * @return 聊天结果
     */
    @Override
    public GenerationResult chatWithMsg(ChatDto chatDto) {
        Generation gen = new Generation();
        GenerationParam param = getGenerationParam(chatDto);
        try {
            return gen.call(param);
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new AllApiException(PlatformEnum.ALI, e.getMessage());
        }
    }

    /**
     * 处理聊天参数
     *
     * @param chatDto 聊天参数
     * @return 生成参数
     */
    private GenerationParam getGenerationParam(ChatDto chatDto) {
        GenerationParam.GenerationParamBuilder<?, ?> builder = GenerationParam.builder()
                .apiKey(aliProperties.getBaiLian().getApiKey())
                .model(chatDto.getModelEnum().toString())
                .messages(chatDto.getMessages());
        ObjUtil.setIfNotNull(builder::temperature, chatDto.getTemperature());
        ObjUtil.setIfNotNull(builder::prompt, chatDto.getPrompt());
        ObjUtil.setIfNotNull(builder::topP, chatDto.getTopP());
        ObjUtil.setIfNotNull(builder::topK, chatDto.getTopK());
        ObjUtil.setIfNotNull(builder::repetitionPenalty, chatDto.getRepetitionPenalty());
        ObjUtil.setIfNotNull(builder::maxTokens, chatDto.getMaxTokens());
        ObjUtil.setIfNotNull(builder::seed, chatDto.getSeed());
        ObjUtil.setIfNotNull(builder::incrementalOutput, chatDto.getIncrementalOutput());
        ObjUtil.setIfNotNull(builder::resultFormat, chatDto.getResultFormat());
        ObjUtil.setIfNotNull(builder::stopStrings, chatDto.getStop());
        ObjUtil.setIfNotNull(builder::tools, chatDto.getTools());
        ObjUtil.setIfNotNull(builder::toolChoice, chatDto.getToolChoice());
        ObjUtil.setIfNotNull(builder::enableSearch, chatDto.getEnableSearch());
        ObjUtil.setIfNotNull(builder::searchOptions, chatDto.getSearchOptions());
        return builder.build();
    }


}
