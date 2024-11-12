package com.tamako.allapi.api;


import com.tamako.allapi.volcengine.enums.trc.PrivilegesEnum;

import java.util.Date;
import java.util.Map;

/**
 * 火山引擎实时音视频
 *
 * @author Tamako
 * @since 2024/11/12 14:30
 */
public interface VolcEngineRTCApi {

    String getAccessToken(String userId, String roomId, Date expireTime, Map<PrivilegesEnum, Date> privileges);
}
