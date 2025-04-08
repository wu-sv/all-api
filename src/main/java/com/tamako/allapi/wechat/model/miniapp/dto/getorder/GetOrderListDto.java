package com.tamako.allapi.wechat.model.miniapp.dto.getorder;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取订单列表请求参数
 *
 * @author Tamako
 * @since 2025/4/8 17:16
 */
@Data
@Accessors(chain = true)
public class GetOrderListDto {
    /**
     * 支付时间所属范围。
     */
    @Alias("pay_time_range")
    private PayTimeRange payTimeRange;
    /**
     * 订单状态枚举：(1) 待发货；(2) 已发货；(3) 确认收货；(4) 交易完成；(5) 已退款；(6) 资金待结算。
     */
    @Alias("order_state")
    private String orderState;
    /**
     * 支付者openid。
     */
    private String openid;
    /**
     * 翻页时使用，获取第一页时不用传入，如果查询结果中 has_more 字段为 true，则传入该次查询结果中返回的 last_index 字段可获取下一页。
     */
    @Alias("last_index")
    private String lastIndex;
    /**
     * 翻页时使用，返回列表的长度，默认为100
     */
    @Alias("page_size")
    private String pageSize;
}
