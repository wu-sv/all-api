package com.tamako.allapi.ali.model.bailian.dto;


import com.alibaba.dashscope.aigc.conversation.ConversationParam;
import com.alibaba.dashscope.aigc.generation.SearchOptions;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.tools.ToolBase;
import com.tamako.allapi.ali.enums.bailian.ModelEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Tamako
 * @since 2025/2/11 17:01
 */
@Data
@Accessors(chain = true)
public class ChatDto {
    /**
     * 由历史对话组成的消息列表
     */
    @NotNull
    private List<Message> messages;

    /**
     * 模型名称。
     * 支持的模型：通义千问大语言模型（商业版、开源版、Qwen-Long）、通义千问VL、通义千问Audio、数学模型、代码模型
     */
    @NotNull
    private ModelEnum modelEnum;

    /**
     * 采样温度，控制模型生成文本的多样性。
     * temperature越高，生成的文本更多样，反之，生成的文本更确定。
     * 取值范围： [0, 2)
     */
    private Float temperature;

    /**
     * 提示信息，模型会基于提示信息生成文本。
     */
    private String prompt;

    /**
     * 核采样的概率阈值，控制模型生成文本的多样性。
     * top_p越高，生成的文本更多样。反之，生成的文本更确定。
     * 取值范围：（0,1.0]。
     */
    private Double topP;

    /**
     * 生成过程中采样候选集的大小。例如，取值为50时，仅将单次生成中得分最高的50个Token组成随机采样的候选集。取值越大，生成的随机性越高；取值越小，生成的确定性越高。取值为None或当top_k大于100时，表示不启用top_k策略，此时仅有top_p策略生效。
     * 取值需要大于或等于0。
     * qwen-math 系列、qwen-vl 系列默认值为1，其余均为20。
     */
    private Integer topK;

    /**
     * 模型生成时连续序列中的重复度。提高repetition_penalty时可以降低模型生成的重复度，1.0表示不做惩罚。没有严格的取值范围，只要大于0即可。
     */
    private Float repetitionPenalty;

    /**
     * 本次请求返回的最大 Token 数。
     * max_tokens 的设置不会影响大模型的生成过程，如果模型生成的 Token 数超过max_tokens，本次请求会返回截断后的内容。
     * 默认值和最大值都是模型的最大输出长度。关于各模型的最大输出长度，请参见模型列表。
     * max_tokens参数适用于需要限制字数（如生成摘要、关键词）、控制成本或减少响应时间的场景。
     */
    private Integer maxTokens;

    /**
     * 设置seed参数会使文本生成过程更具有确定性，通常用于使模型每次运行的结果一致。
     * 在每次模型调用时传入相同的seed值（由您指定），并保持其他参数不变，模型将尽可能返回相同的结果。
     * 取值范围：0到2^31−1。
     */
    private Integer seed;

    /**
     * 在流式输出模式下是否开启增量输出
     */
    private Boolean incrementalOutput = true;

    /**
     * 返回结果的格式，默认为text，也可选择message。推荐您优先使用message格式
     * 参考：GenerationParam.ResultFormat
     */
    private String resultFormat = ConversationParam.ResultFormat.MESSAGE;

    /**
     * 使用stop参数后，当模型生成的文本即将包含指定的字符串或token_id时，将自动停止生成。
     * 您可以在stop参数中传入敏感词来控制模型的输出。
     * stop为array类型时，不可以将token_id和字符串同时作为元素输入，比如不可以指定stop为["你好",104307]。
     */
    private List<String> stop;

    /**
     * 可供模型调用的工具数组，可以包含一个或多个工具对象。一次 Function Calling 流程模型会从中选择其中一个工具。使用 tools 时需要同时指定result_format参数为message。在Function Calling流程中，无论是发起Function Calling，还是向模型提交工具函数的执行结果，均需设置tools参数。
     * 目前不支持通义千问VL/Audio，也不建议用于数学和代码模型。
     */
    private List<ToolBase> tools;

    /**
     * 在使用tools参数时，用于控制模型调用指定工具。有三种取值：
     * "none"表示不调用工具。tools参数为空时，默认值为"none"。
     * "auto"表示由模型判断是否调用工具，可能调用也可能不调用。tools参数不为空时，默认值为"auto"。
     * object结构可以指定模型调用的工具。例如tool_choice={"type": "function", "function": {"name": "user_function"}}。
     * type只支持指定为"function"。
     * function
     * name表示期望被调用的工具名称，例如"get_current_time"。
     */
    private Object toolChoice;

    /**
     * 模型在生成文本时是否使用互联网搜索结果进行参考。取值如下：
     * true：启用互联网搜索，模型会将搜索结果作为文本生成过程中的参考信息，但模型会基于其内部逻辑判断是否使用互联网搜索结果。
     * 如果模型没有搜索互联网，建议优化Prompt。
     * false（默认）：关闭互联网搜索。
     * 若您的业务场景中需要使用互联网搜索能力，请配置该参数为true。
     * 启用互联网搜索功能可能会增加 Token 的消耗。
     * 当前支持 qwen-max、qwen-plus、qwen-turbo 系列模型。
     */
    private Boolean enableSearch;

    /**
     * 联网搜索的策略。仅当enable_search为true时生效。
     */
    private SearchOptions searchOptions;

}
