package com.lzy.java8tpl.api;

public class ResultHelper {

    public static <T> Result<T> success() {
        return new Result<T>()
                .setCode(ErrorCode.OK.getCode())
                .setMsg(ErrorCode.OK.getMsg())
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(ErrorCode.OK.getCode())
                .setMsg(ErrorCode.OK.getMsg())
                .setData(data)
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<T>()
                .setCode(errorCode.getCode())
                .setMsg(errorCode.getMsg())
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> error(ErrorCode errorCode, String msg) {
        return new Result<T>()
                .setCode(errorCode.getCode())
                .setMsg(msg)
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> error(String code, String msg) {
        return new Result<T>()
                .setCode(code)
                .setMsg(msg)
                .setTimestamp(System.currentTimeMillis());
    }


    public static <T> Result<T> serviceError() {
        return new Result<T>()
                .setCode(ErrorCode.SERVICE_ERROR.getCode())
                .setMsg(ErrorCode.SERVICE_ERROR.getMsg())
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> serviceError(String msg) {
        return new Result<T>()
                .setCode(ErrorCode.SERVICE_ERROR.getCode())
                .setMsg(msg)
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> paramError() {
        return new Result<T>()
                .setCode(ErrorCode.PARAM_ERROR.getCode())
                .setMsg(ErrorCode.PARAM_ERROR.getMsg())
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> paramError(String msg) {
        return new Result<T>()
                .setCode(ErrorCode.PARAM_ERROR.getCode())
                .setMsg(msg)
                .setTimestamp(System.currentTimeMillis());
    }
}
