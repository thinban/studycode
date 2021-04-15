package com.example.thinbanrest.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务异常类.
 *
 * @author yangbin
 */
@Slf4j
@Getter
public class BizException extends RuntimeException {

    /**
     * 异常码.
     */
    private String code;

    public BizException(String message) {
        super(message);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
        log.warn("code: " + code + ", message: " + message);
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(SysMsg sysMsg) {
        this(sysMsg.getCode(), sysMsg.getMsg());
    }
}
