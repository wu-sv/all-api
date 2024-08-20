package com.tamako.allapi.wechat.model.miniapp.dto.uploadshop.uploadshippinginfodto;


import lombok.Data;

import java.util.List;

/**
 * @author Tamako
 * @data 2024/8/20 16:57
 */
@Data
public class Shipping {
    private String trackingNo;
    private String expressCompany;
    private List<Item> itemList;
    private Contact contact;
}
