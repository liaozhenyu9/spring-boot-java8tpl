package com.lzy.java8tpl.api;

public class ServiceErrorException extends ApiException {

    public ServiceErrorException() {
        super(ErrorCode.SERVICE_ERROR);
    }

    public ServiceErrorException(String msg) {
        super(ErrorCode.SERVICE_ERROR, msg);
    }

    public ServiceErrorException(String msg, Throwable cause) {
        super(ErrorCode.SERVICE_ERROR, msg, cause);
    }

}
