package com.tamako.allapi.wechat.model.wxpay.domin;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.IoUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * 微信平台证书
 *
 * @author Tamako
 * @since 2025/5/26 17:03
 */
@Data
@Accessors(chain = true)
public class PlatFromCert {
    /**
     * 平台证书序列号
     */
    private String serialNo;
    /**
     * 平台证书IO流
     */
    private String cert;
    /**
     * 缓存持有到期时间（当前时间+11个小时）
     */
    private DateTime expireTime;

    /**
     * 获取平台证书IO流
     *
     * @return 平台证书IO流
     */
    public InputStream getCert() {
        return IoUtil.toUtf8Stream(cert);
    }
}
