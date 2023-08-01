package com.lzy.java8tpl.api;

import com.lzy.java8tpl.util.MDCUtils;

public class ResultHelper {

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(ErrorCode.OK.getCode())
                .setMsg(ErrorCode.OK.getMsg())
                .setData(data)
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> error(ErrorCode errorCode, String msg) {
        return error(errorCode.getCode(), msg);
    }

    public static <T> Result<T> error(String code, String msg) {
        return error(code, msg, MDCUtils.getRequestId());
    }

    public static <T> Result<T> error(String code, String msg, String requestId) {
        return new Result<T>()
                .setCode(code)
                .setMsg(msg)
                .setRequestId(requestId)
                .setTimestamp(System.currentTimeMillis());
    }

    public static <T> Result<T> serviceError() {
        return error(ErrorCode.SERVICE_ERROR);
    }

    public static <T> Result<T> serviceError(String msg) {
        return error(ErrorCode.SERVICE_ERROR, msg);
    }

    public static <T> Result<T> paramError() {
        return error(ErrorCode.PARAM_ERROR);
    }

    public static <T> Result<T> paramError(String msg) {
        return error(ErrorCode.PARAM_ERROR, msg);
    }
}
