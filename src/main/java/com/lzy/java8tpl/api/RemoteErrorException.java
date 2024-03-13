package com.lzy.java8tpl.api;

public class RemoteErrorException extends ApiException {

    private String extSystemErrorCode;

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

    public RemoteErrorException(String extSystemErrorCode, String msg) {
        super(ErrorCode.REMOTE_ERROR, msg);
        this.extSystemErrorCode = extSystemErrorCode;
    }

    public RemoteErrorException(String extSystemErrorCode, String msg, Throwable cause) {
        super(ErrorCode.REMOTE_ERROR, msg, cause);
        this.extSystemErrorCode = extSystemErrorCode;
    }

    public String getExtSystemErrorCode() {
        return extSystemErrorCode;
    }

}
