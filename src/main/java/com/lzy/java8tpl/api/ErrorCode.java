package com.lzy.java8tpl.api;

import lombok.Getter;

@Getter
public enum ErrorCode {

    OK("10000","操作成功"),

    //A 表示错误源于用户，例如参数错误、版本过低或支付超时。
    CLIENT_ERROR("A0001", "客户端错误"),
    PARAM_ERROR("A0002", "参数错误"),
    SIGN_ERROR("A0003", "签名错误"),
    USER_NOT_FOUND("A0010", "用户不存在"),
    USER_ALREADY_EXISTS("A0011", "用户已存在"),
    USERNAME_PASSWORD_INCORRECT("A0012", "用户名或密码错误"),
    VERIFICATION_CODE_EXPIRED("A0013", "验证码已过期"),
    BAD_CREDENTIALS_EXPIRED("A0014", "用户认证异常"),

    //B 表示错误源于当前系统，通常是由于业务逻辑错误或程序健壮性不足。
    SERVICE_ERROR("B0001", "系统内部错误"),
    SERVICE_TIMEOUT_ERROR("B0010", "系统执行超时"),

    //C 表示错误源于第三方服务，例如 CDN 服务故障或消息投递超时。
    REMOTE_ERROR("C0001", "第三方服务错误");

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final String code;

    private final String msg;
}
