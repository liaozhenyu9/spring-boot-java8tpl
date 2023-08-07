package com.lzy.java8tpl.config;

import com.lzy.java8tpl.interceptor.SignInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加SignatureInterceptor拦截器，并指定拦截的路径
        registry.addInterceptor(new SignInterceptor("xxxxxxxx"))
                .addPathPatterns("/**"); // 可以根据实际的请求路径进行配置
    }
}
