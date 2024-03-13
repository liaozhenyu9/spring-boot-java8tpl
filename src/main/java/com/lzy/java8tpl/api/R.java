package com.lzy.java8tpl.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> {

    private String code;
    private String msg;
    private T data;
    private Long ts;
    private String requestId;

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>()
                .setCode(ErrorCode.OK.getCode())
                .setMsg(ErrorCode.OK.getMsg())
                .setData(data)
                .setTs(System.currentTimeMillis());
    }

    public static R error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static R error(ErrorCode errorCode, String msg) {
        return error(errorCode.getCode(), msg);
    }

    public static <T> R<T> error(ErrorCode errorCode, String msg, T data) {
        return error(errorCode.getCode(), msg, data);
    }

    public static R error(String code, String msg) {
        return error(code, msg, null);
    }

    public static <T> R<T> error(String code, String msg, T data) {
        return new R<T>()
                .setCode(code)
                .setMsg(msg)
                .setData(data)
                .setTs(System.currentTimeMillis());
    }

    public static R serviceError() {
        return error(ErrorCode.SERVICE_ERROR);
    }

    public static R serviceError(String msg) {
        return error(ErrorCode.SERVICE_ERROR, msg);
    }

    public static <T> R<T> serviceError(String msg, T data) {
        return error(ErrorCode.SERVICE_ERROR, msg, data);
    }

    public static R paramError() {
        return error(ErrorCode.PARAM_ERROR);
    }

    public static R paramError(String msg) {
        return error(ErrorCode.PARAM_ERROR, msg);
    }

    public static R remoteError() {
        return error(ErrorCode.REMOTE_ERROR);
    }

    public static R remoteError(String msg) {
        return error(ErrorCode.REMOTE_ERROR, msg);
    }
}
