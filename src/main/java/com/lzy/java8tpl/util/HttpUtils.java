package com.lzy.java8tpl.util;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    public static Map<String, Object> getRequestHeader(HttpServletRequest request) {
        Map<String, Object> headerParams = new HashMap<>(32);
        // 获取所有请求头名称并遍历
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerParams.put(headerName, headerValue);
        }
        return headerParams;
    }

    public static boolean isFileUploadContentType(HttpServletRequest request) {
        String contentType = request.getHeader("content-type");
        if (contentType != null && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            return true;
        }
        return false;
    }

    public static boolean isJsonResponse(HttpServletResponse response) {
        String contentType = response.getContentType();
        return contentType != null && contentType.contains("application/json");
    }


}
