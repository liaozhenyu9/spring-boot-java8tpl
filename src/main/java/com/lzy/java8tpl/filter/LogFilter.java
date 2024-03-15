package com.lzy.java8tpl.filter;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.util.CachedBodyHttpServletRequest;
import com.lzy.java8tpl.util.HttpUtils;
import com.lzy.java8tpl.util.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Slf4j
public class LogFilter extends OncePerRequestFilter {

    public static final String X_REQUEST_ID = "X-Request-Id";

    private static final Set<String> IGNORE_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/**/swagger-ui/**", "/**/swagger-resources/**", "/**/api-docs/**", "/**/webjars/**", "/favicon.ico", "/actuator/health")));

    private final PathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String requestId = request.getHeader(X_REQUEST_ID);
            MDCUtils.setRequestId(requestId);
            // 忽略指定url
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            boolean isIgnore = IGNORE_PATHS.stream().anyMatch(ignore -> matcher.match(ignore, path));
            if (isIgnore) {
                chain.doFilter(request, response);
                return;
            }
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            log.info("<<< REQUEST [{}] [{}] [{}] [{}]", request.getRequestURI(), request.getMethod(), request.getProtocol(), request.getRemoteAddr());
            log.info("<<< REQUEST HEADERS   : {}", JSON.toJSONString(HttpUtils.getRequestHeader(request)));
            if (!CollectionUtils.isEmpty(request.getParameterMap())) {
                log.info("<<< REQUEST PARAMETERS: {}", JSON.toJSONString(request.getParameterMap()));
            }
            //忽略文件上传
            if (HttpUtils.isFileUploadContentType(request)) {
                chain.doFilter(request, response);
                return;
            }

            CachedBodyHttpServletRequest requestWrapper = new CachedBodyHttpServletRequest(request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            if (!"GET".equalsIgnoreCase(requestWrapper.getMethod())) {
                log.info("<<< REQUEST BODY [{}]: {}", requestWrapper.getRequestURI(), requestWrapper.getBodyString());
            }
            chain.doFilter(requestWrapper, responseWrapper);
            //将request-id写入响应头
            responseWrapper.addHeader(X_REQUEST_ID, requestId);
            if (HttpUtils.isContentTypeContainingJson(responseWrapper)) {
                log.info(">>> RESPONSE BODY [{}]: {}", requestWrapper.getRequestURI(), new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
            }
            stopWatch.stop();
            log.info(">>> RESPONSE CODE: [{}] ({}ms) [{}] [{}]", responseWrapper.getStatus(), stopWatch.getTotalTimeMillis(), responseWrapper.getContentType(), requestWrapper.getRequestURI());
            // remember to respond to the client with the cached data.
            responseWrapper.copyBodyToResponse();
        } finally {
            MDC.clear();
        }
    }
}
