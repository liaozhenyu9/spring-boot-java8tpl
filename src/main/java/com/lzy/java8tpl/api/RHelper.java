package com.lzy.java8tpl.api;

import com.lzy.java8tpl.util.MDCUtils;

public class RHelper {

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> success(T data) {
        return new R<T>()
                .setCode(ErrorCode.OK.getCode())
                .setMsg(ErrorCode.OK.getMsg())
                .setData(data)
                .setTs(System.currentTimeMillis());
    }

    public static <T> R<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> R<T> error(ErrorCode errorCode, String msg) {
        return error(errorCode.getCode(), msg);
    }

    public static <T> R<T> error(String code, String msg) {
        return error(code, msg, MDCUtils.getRequestId());
    }

    public static <T> R<T> error(String code, String msg, String requestId) {
        return new R<T>()
                .setCode(code)
                .setMsg(msg)
                .setRequestId(requestId)
                .setTs(System.currentTimeMillis());
    }

    public static <T> R<T> serviceError() {
        return error(ErrorCode.SERVICE_ERROR);
    }

    public static <T> R<T> serviceError(String msg) {
        return error(ErrorCode.SERVICE_ERROR, msg);
    }

    public static <T> R<T> paramError() {
        return error(ErrorCode.PARAM_ERROR);
    }

    public static <T> R<T> paramError(String msg) {
        return error(ErrorCode.PARAM_ERROR, msg);
    }
}
