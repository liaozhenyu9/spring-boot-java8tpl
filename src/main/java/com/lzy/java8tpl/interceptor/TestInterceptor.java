package com.lzy.java8tpl.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("test interceptor..........................");
        // 从请求中获取参数 app_id, timestamp, biz_content
        String appId = request.getParameter("app_id");
        String timestamp = request.getParameter("timestamp");
        String bizContent = request.getParameter("biz_content");

        Map<String, String[]> parameterMap = request.getParameterMap();

        Enumeration<String> parameterNames = request.getParameterNames();



        // 进行验签逻辑，根据具体需求实现

        // 如果验签失败，可以返回错误信息或直接抛出异常

        return true; // 验签通过，继续执行后续拦截器或Controller方法
    }
}
