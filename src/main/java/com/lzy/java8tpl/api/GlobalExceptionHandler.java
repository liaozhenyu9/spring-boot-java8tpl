package com.lzy.java8tpl.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //http请求方法错误，常见post接口用了get请求
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        printErrorLog(request.getRequestURI(), ex);
        return R.paramError("请求方法错误: " + ex.getMessage());
    }

    //@RequestAttribute 修饰参数为空时会触发
    @ExceptionHandler(ServletRequestBindingException.class)
    public R<Void> handleServletRequestBindingException(HttpServletRequest request, ServletRequestBindingException ex) {
        printErrorLog(request.getRequestURI(), ex);
        return R.paramError();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Void> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        printErrorLog(request.getRequestURI(), ex);
        return R.paramError("参数格式错误");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<Void> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        printErrorLog(request.getRequestURI(), ex);
        return R.paramError(ex.getMessage());
    }

    /**
     * 单个参数校验异常抛出ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Void> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        printErrorLog(request.getRequestURI(), ex);
        String msg = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
        return R.paramError(msg);
    }

    /**
     * 使用form data, json方式调用接口，校验异常抛出 BindException
     */
    @ExceptionHandler(BindException.class)
    public R<Void> handleBindException(HttpServletRequest request, BindException ex) {
        printErrorLog(request.getRequestURI(), ex);
        String msg = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
        return R.paramError(msg);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public R<Void> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        printErrorLog(request.getRequestURI(), ex);
        return R.serviceError(ex.getMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = {ApiException.class})
    public R<Void> handleApiException(HttpServletRequest request, ApiException ex) {
        printErrorLog(request.getRequestURI(), ex);
        return R.error(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(value = Throwable.class)
    public R<Void> handleThrowable(HttpServletRequest request, Throwable throwable) {
        printErrorLog(request.getRequestURI(), throwable);
        return R.serviceError();
    }

    public void printErrorLog(String requestURI, Throwable throwable) {
        log.error("{} [throwable]", requestURI, throwable);
    }
}
