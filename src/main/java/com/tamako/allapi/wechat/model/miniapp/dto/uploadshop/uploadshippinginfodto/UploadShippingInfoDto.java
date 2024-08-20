package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.OrderKey;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Tamako
 * @data 2024/8/20 13:21
 */
@Data
public class UploadShippingInfoDto {
    private OrderKey orderKey;
    private Integer deliveryMode;
    private List<Shipping> shippingList;
    private ZonedDateTime uploadTime;
}
