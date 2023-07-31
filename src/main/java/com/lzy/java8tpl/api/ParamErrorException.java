package com.lzy.java8tpl.api;

public class ParamErrorException extends ApiException {

    public ParamErrorException() {
        super(ErrorCode.PARAM_ERROR);
    }

    public ParamErrorException(String msg) {
        super(ErrorCode.PARAM_ERROR, msg);
    }

    public ParamErrorException(String msg, Throwable cause) {
        super(ErrorCode.PARAM_ERROR, msg, cause);
    }

}
