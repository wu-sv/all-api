package com.tamako.allapi.api.impl;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.api.VolcEngineRTCApi;
import com.tamako.allapi.configuration.VolcEngineProperties;
import com.tamako.allapi.utils.DateUtil;
import com.tamako.allapi.volcengine.enums.trc.PrivilegesEnum;
import com.tamako.allapi.volcengine.model.rtc.domian.AccessToken;

import java.util.Date;
import java.util.Map;

/**
 * VolcEngine RTC API实现类
 *
 * @author Tamako
 * @since 2024/11/12 14:51
 */
public class VolcEngineRTCApiImpl implements VolcEngineRTCApi {
    /**
     * 日志
     */
    private static final Log log = LogFactory.get();
    /**
     * 火山引擎配置
     */
    private final VolcEngineProperties volcEngineProperties;

    /**
     * 构造方法
     *
     * @param volcEngineProperties 火山引擎配置
     */
    public VolcEngineRTCApiImpl(VolcEngineProperties volcEngineProperties) {
        this.volcEngineProperties = volcEngineProperties;
    }

    /**
     * 获取AccessToken
     *
     * @param userId     用户Id
     * @param roomId     房间Id(支持通配符“*”)
     * @param expireTime 过期时间
     * @param privileges 权限(指定权限和权限的过期时间)
     * @return AccessToken
     */
    @Override
    public String getAccessToken(String userId, String roomId, Date expireTime, Map<PrivilegesEnum, Date> privileges) {
        AccessToken accessToken = new AccessToken(
                volcEngineProperties.getRtc().getAppId(),
                volcEngineProperties.getRtc().getAppKey(),
                roomId, userId);
        accessToken.expireTime(DateUtil.getTimestamp(expireTime));
        privileges.forEach((k, v) -> accessToken.addPrivilege(k, DateUtil.getTimestamp(v)));
        return accessToken.serialize();
    }


}
