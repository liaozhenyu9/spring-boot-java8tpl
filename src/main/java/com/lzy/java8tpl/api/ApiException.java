package com.lzy.java8tpl.api;

import lombok.Getter;
import java.util.Optional;

@Getter
public class ApiException extends RuntimeException {

    private final String code;

    private final String msg;

    public ApiException(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public ApiException(ErrorCode errorCode, String msg) {
        this(errorCode, msg, null);
    }

    public ApiException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, null, cause);
    }

    public ApiException(ErrorCode errorCode, String msg, Throwable cause) {
        super(Optional.ofNullable(msg).orElse(errorCode.getMsg()), cause);
        this.code = errorCode.getCode();
        this.msg = Optional.ofNullable(msg).orElse(errorCode.getMsg());
    }
}
