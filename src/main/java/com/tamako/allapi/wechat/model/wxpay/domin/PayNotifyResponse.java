package com.tamako.allapi.wechat.model.wxpay.domin;


import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.tamako.allapi.exception.AllApiException;
import com.tamako.allapi.exception.PlatformEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 支付回调响应
 *
 * @author Tamako
 * @since 2025/5/28 09:58
 */
@Data
@Accessors(chain = true)
public class PayNotifyResponse {
    /**
     * 状态
     */
    private String code;
    /**
     * 具体消息
     */
    private String message;

    /**
     * 成功
     *
     * @param response 响应
     */
    public void success(HttpServletResponse response) {
        this.code = "SUCCESS";
        this.message = "成功";
        sendMsg(HttpStatus.HTTP_OK, response);
    }


    /**
     * 失败
     *
     * @param response 响应
     */
    public void fail(HttpServletResponse response) {
        this.code = "FAIL";
        this.message = "失败";
        sendMsg(HttpStatus.HTTP_INTERNAL_ERROR, response);
    }

    /**
     * 发送消息
     *
     * @param status   状态
     * @param response 响应
     */
    public void sendMsg(Integer status, HttpServletResponse response) {
        try {
            response.getOutputStream().write(JSONUtil.toJsonStr(this).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (IOException e) {
            throw new AllApiException(PlatformEnum.WX, "文件读写流错误", e);
        }
    }
}
