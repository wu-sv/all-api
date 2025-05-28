package com.tamako.allapi.ali.model.nls.vo;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 翻译结果
 *
 * @author Tamako
 * @since 2025/1/17 10:09
 */
@Data
@Accessors(chain = true)
public class TransResult {
    /**
     * 词信息，获取时需设置enable_words为true，且设置服务version为”4.0”。
     */
    @Alias("Words")
    private List<WordResult> words;
    /**
     * 识别的结果数据。当StatusText为SUCCEED时存在。
     */
    @Alias("Sentences")
    private List<SentenceResult> sentences;
}
