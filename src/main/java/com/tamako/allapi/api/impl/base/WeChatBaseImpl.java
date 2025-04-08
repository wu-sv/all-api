package com.tamako.allapi.api.impl.base;


import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tamako.allapi.configuration.properties.WechatProperties;
import com.tamako.allapi.utils.JSONUtil;
import com.tamako.allapi.wechat.constants.MiniAppUrlConstant;
import com.tamako.allapi.wechat.model.miniapp.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tamako
 * @since 2025/2/11 14:56
 */
@RequiredArgsConstructor
public class WeChatBaseImpl {
    /**
     * 日志
     */
    protected static final Log log = LogFactory.get();
    /**
     * 微信配置
     */
    protected final WechatProperties wechatProperties;

    /**
     * 转换为ResponseVo
     *
     * @param jsonObject json对象
     * @return ResponseVo
     */
    protected ResponseVo toResponseVo(JSONObject jsonObject) {
        return JSONUtil.toBean(jsonObject, ResponseVo.class);
    }

    /**
     * 创建UrlBuilder
     *
     * @param path        path
     * @param accessToken access_token
     * @return UrlBuilder
     */
    protected UrlBuilder createUrlBuilderWithAccessToken(@NotNull String path, @NotNull String accessToken) {
        return UrlBuilder.of()
                .setScheme("https")
                .setHost(MiniAppUrlConstant.WECHAT_API_URL)
                .addPath(path)
                .addQuery("access_token", accessToken);
    }

    /**
     * 获取带access_token的url
     *
     * @param path        path
     * @param accessToken access_token
     * @return url
     */
    protected String createUrlWithAccessToken(@NotNull String path, @NotNull String accessToken) {
        return createUrlBuilderWithAccessToken(path, accessToken).build();
    }
}
