package com.lzy.java8tpl.filter;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class LogMDCFilter extends OncePerRequestFilter {

    private final static String REQUEST_ID = "requestId";

    private static final String KEY_REQUEST_ID = "X-Request-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String requestId = request.getHeader(REQUEST_ID);
            if (!StringUtils.hasText(requestId)) {
                requestId = UUID.randomUUID().toString();
            }
            //将生成的request-id放入MDC中，允许打印日志时获取
            MDC.put(REQUEST_ID, requestId);

            //将request-id写入响应头
            response.addHeader(KEY_REQUEST_ID, requestId);
            //传入其他过滤器
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
