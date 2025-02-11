package com.tamako.allapi.api.impl;


import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.tamako.allapi.ali.model.bailian.dto.ChatDto;
import com.tamako.allapi.api.AliBaiLianApi;
import com.tamako.allapi.api.impl.base.AliBaseImpl;
import com.tamako.allapi.configuration.properties.AliProperties;

/**
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

    public void chat(ChatDto chatDto) {
        GenerationParam.GenerationParamBuilder<?, ?> builder = GenerationParam.builder()
                .apiKey(aliProperties.getBaiLian().getApiKey())
                .model(chatDto.getModelEnum().toString())
                .messages(chatDto.getMessages())
                .temperature(chatDto.getTemperature())
                .prompt(chatDto.getPrompt())
                .topP(chatDto.getTopP())
                .topK(chatDto.getTopK())
                .repetitionPenalty(chatDto.getRepetitionPenalty())
                .maxTokens(chatDto.getMaxTokens())
                .seed(chatDto.getSeed())
                .incrementalOutput(chatDto.getIncrementalOutput())
                .resultFormat(chatDto.getResultFormat())
                .stopStrings(chatDto.getStop())
                .tools(chatDto.getTools())
                .toolChoice(chatDto.getToolChoice())
                .enableSearch(chatDto.getEnableSearch())
                .searchOptions(chatDto.getSearchOptions());



    }

}
