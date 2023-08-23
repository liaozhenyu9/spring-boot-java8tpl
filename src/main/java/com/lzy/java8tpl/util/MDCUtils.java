package com.lzy.java8tpl.util;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import java.util.UUID;

public class MDCUtils {
    public final static String REQUEST_ID = "requestId";

    public static void setRequestId() {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
    }

    public static void setRequestId(String requestId) {
        if (!StringUtils.hasText(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        //将生成的request-id放入MDC中，允许打印日志时获取
        MDC.put(REQUEST_ID, requestId);
    }

    public static String getRequestId() {
        return MDC.get(REQUEST_ID);
    }
}
