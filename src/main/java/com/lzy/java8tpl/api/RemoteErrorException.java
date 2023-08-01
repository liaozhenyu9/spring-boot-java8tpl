package com.lzy.java8tpl.api;

public class RemoteErrorException extends ApiException {

    public RemoteErrorException() {
        super(ErrorCode.REMOTE_ERROR);
    }

    public RemoteErrorException(String msg) {
        super(ErrorCode.REMOTE_ERROR, msg);
    }

    public RemoteErrorException(Throwable cause) {
        super(ErrorCode.REMOTE_ERROR, cause);
    }

    public RemoteErrorException(String msg, Throwable cause) {
        super(ErrorCode.REMOTE_ERROR, msg, cause);
    }

}
