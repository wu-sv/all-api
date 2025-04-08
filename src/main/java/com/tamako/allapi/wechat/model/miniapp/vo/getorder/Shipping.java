package com.tamako.allapi.wechat.model.miniapp.vo.getorder;


import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物流信息
 *
 * @author Tamako
 * @since 2025/4/8 14:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Shipping extends com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto.Shipping {
    /**
     * 该物流信息的上传时间，时间戳形式。
     */
    @Alias("upload_time")
    private Integer uploadTime;
}
