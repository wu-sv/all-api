package com.tamako.allapi.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常基类
 *
 * @author Tamako
 * @since 2024/10/8 09:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AllApiException extends RuntimeException {
    /**
     * 平台枚举
     */
    private PlatformEnum platformEnum;
    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;

    /**
     * 构造函数
     *
     * @param platformEnum 平台枚举
     * @param code         错误码
     * @param msg          错误信息
     */
    public AllApiException(PlatformEnum platformEnum, String code, String msg) {
        super(msg);
        this.platformEnum = platformEnum;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数
     *
     * @param platformEnum 平台枚举
     * @param msg          错误信息
     * @param cause        异常
     */
    public AllApiException(PlatformEnum platformEnum, String msg, Throwable cause) {
        super(platformEnum.getName() + "接口调用:" + msg, cause);
        this.platformEnum = platformEnum;
        this.msg = msg;
    }

    /**
     * 构造函数
     *
     * @param platformEnum 平台枚举
     * @param msg          错误信息
     */
    public AllApiException(PlatformEnum platformEnum, String msg) {
        super(msg);
        this.platformEnum = platformEnum;
        this.msg = msg;
    }

    /**
     * 构造函数
     *
     * @param cause 异常
     */
    public AllApiException(Throwable cause) {
        super("调用接口失败", cause);
    }

    /**
     * 构造函数
     *
     * @param msg 错误信息
     */
    public AllApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    /**
     * toString方法
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        if (this.code == null && this.msg == null && super.getCause() != null) {
            return super.toString();
        } else if (this.code != null && this.msg != null) {
            return "调用" + this.platformEnum.getName() + "接口返回失败，失败的转态码为：" + this.code + ",失败的原因：" + this.msg;
        } else {
            return this.msg;
        }
    }
}
