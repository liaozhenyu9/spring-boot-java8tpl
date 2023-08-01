package com.lzy.java8tpl.aspect;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Aspect
public class ControllerLogAspect {

    @Pointcut("execution(* com.lzy.java8tpl.controller..*(..)) && !@annotation(com.lzy.java8tpl.annotation.ExcludeControllerLog)")
    public void controllerLogPointCut() {}

    @Before("controllerLogPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            log.info("<======== REQUEST START {} {} {}", request.getMethod(), request.getRequestURL().toString(), request.getRemoteAddr());
            log.info("Headers       : {}", JSON.toJSONString(this.getRequestHeader(request)));
            log.info("Request params: {}", JSON.toJSONString(this.getRequestParams(joinPoint)));
        } catch (Exception e) {
            log.error("An exception occurred while logging global request information:", e);
        }
    }

    @Around("controllerLogPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        try {
            log.info("Response params: {}", JSON.toJSONString(result));
            log.info("========> RESPONSE END ({}ms)", stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            log.error("An exception occurred while logging global request information:", e);
        }
        return result;
    }

    /**
     * 获取入参
     *
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Map<String, Object> requestParams = new HashMap<>();
        //参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();
            }
            requestParams.put(paramNames[i], value);
        }
        return requestParams;
    }

    private Map<String, Object> getRequestHeader(HttpServletRequest request) {
        Map<String, Object> headerParams = new HashMap<>(1);
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
