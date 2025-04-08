package com.tamako.allapi.wechat.model.miniapp.vo.getorder;


import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 获取订单返回值
 *
 * @author Tamako
 * @since 2025/4/8 13:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GetOrderVo extends ResponseVo {
    /**
     * 支付单信息
     */
    private Order order;
}
