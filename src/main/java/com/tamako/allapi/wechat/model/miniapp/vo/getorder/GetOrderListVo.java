package com.tamako.allapi.wechat.model.miniapp.vo.getorder;


import cn.hutool.core.annotation.Alias;
import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 获取订单列表返回值
 *
 * @author Tamako
 * @since 2025/4/8 17:11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GetOrderListVo extends ResponseVo {
    /**
     * 翻页时使用。
     */
    @Alias("last_index")
    private String lastIndex;
    /**
     * 是否还有更多支付单。
     */
    @Alias("has_more")
    private Boolean hasMore;
    /**
     * 支付单信息列表。
     */
    @Alias("order_list")
    private List<Order> orderList;
}
