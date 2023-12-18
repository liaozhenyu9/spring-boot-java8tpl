package com.lzy.java8tpl.filter;

import com.lzy.java8tpl.util.CachedBodyHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
@Component
public class InternalSignFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String sign = request.getHeader("sign");
        log.info("sign         : {}", sign);
        CachedBodyHttpServletRequest requestWrapper = null;
        if (request instanceof CachedBodyHttpServletRequest) {
            requestWrapper =  (CachedBodyHttpServletRequest) request;
        } else {
            requestWrapper = new CachedBodyHttpServletRequest(request);
        }

        String bodyString = requestWrapper.getBodyString();
        log.info("bodyString   : {}", bodyString);
        chain.doFilter(requestWrapper, response);
    }
}
