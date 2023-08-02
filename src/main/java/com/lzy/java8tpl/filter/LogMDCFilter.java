package com.lzy.java8tpl.filter;

import com.lzy.java8tpl.util.MDCUtils;
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

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class LogMDCFilter extends OncePerRequestFilter {

    private static final String KEY_REQUEST_ID = "X-Request-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String requestId = request.getHeader(KEY_REQUEST_ID);
            MDCUtils.setRequestId(requestId);

            //将request-id写入响应头
            response.addHeader(KEY_REQUEST_ID, requestId);
            //传入其他过滤器
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
