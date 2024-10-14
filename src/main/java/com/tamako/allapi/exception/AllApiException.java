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
public  class AllApiException extends RuntimeException {
    private PlatformEnum platformEnum;
    private String code;
    private String msg;

    public AllApiException(PlatformEnum platformEnum, String code, String msg) {
        this.platformEnum = platformEnum;
        this.code = code;
        this.msg = msg;
    }

    public AllApiException(PlatformEnum platformEnum, Throwable cause) {
        super(cause);
        this.platformEnum = platformEnum;
    }

    public AllApiException(PlatformEnum platformEnum, String msg) {
        this.platformEnum = platformEnum;
        this.msg = msg;
    }

    public AllApiException(Throwable cause) {
        super(cause);
    }

    public AllApiException(String msg) {
        this.msg = msg;
    }

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
