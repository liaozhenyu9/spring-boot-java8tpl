package com.lzy.java8tpl.filter;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.util.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Slf4j
public class LogMDCFilter extends OncePerRequestFilter {

    private static final String X_REQUEST_ID = "X-Request-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String requestId = request.getHeader(X_REQUEST_ID);
            MDCUtils.setRequestId(requestId);
            log.info("[ACCESS] {} {} {} {}", request.getMethod(), request.getRequestURL().toString(), request.getProtocol(), request.getRemoteAddr());
            log.info("Headers          : {}", JSON.toJSONString(this.getRequestHeader(request)));
            //将request-id写入响应头
            response.addHeader(X_REQUEST_ID, requestId);
            //传入其他过滤器
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private Map<String, Object> getRequestHeader(HttpServletRequest request) {
        Map<String, Object> headerParams = new HashMap<>();
        // 获取所有请求头名称并遍历
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerParams.put(headerName, headerValue);
        }
        return headerParams;
    }
}
