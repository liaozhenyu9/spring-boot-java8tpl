package com.lzy.java8tpl.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SocketTimeoutException.class)
    public Result<Void> handleSocketTimeoutException(HttpServletRequest request, SocketTimeoutException ex) {
        log.error("[{}] {} [ex]", request.getMethod(), request.getRequestURL().toString(), ex);
        return ResultHelper.error(ErrorCode.REMOTE_ERROR, ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Void> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString());
        return ResultHelper.paramError(ex.getMessage());
    }

    /**
     * 单个参数校验异常抛出ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString());
        String msg = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
        return ResultHelper.paramError(msg);
    }

    /**
     * 使用form data, json方式调用接口，校验异常抛出 BindException
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(HttpServletRequest request, BindException ex) {
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString());
        String msg = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
        return ResultHelper.paramError(msg);
    }


    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString());
        return ResultHelper.serviceError(ex.getMessage());
    }

    // 处理自定义异常
    @ExceptionHandler(value = {ApiException.class})
    public Result<Void> handleApiException(HttpServletRequest request, ApiException ex) {
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString());
        return ResultHelper.error(ex.getCode(), ex.getMsg());
    }

    // 兜底处理
    @ExceptionHandler(value = Throwable.class)
    public Result<Void> handleThrowable(HttpServletRequest request, Throwable throwable) {
        log.error("[{}] {} [throwable]", request.getMethod(), request.getRequestURL().toString(), throwable);
        return ResultHelper.serviceError();
    }

}
