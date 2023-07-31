package com.lzy.java8tpl.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;

@Component
@Slf4j
public class RestTemplateUtils {

    private static RestTemplate restTemplate;

    @Resource
    public void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateUtils.restTemplate = restTemplate;
    }

    public static <T> ResponseEntity<T> post1(String url, Object requestBody, Class<T> responseType) {
        return restTemplate.postForEntity(url, requestBody, responseType);
    }


    // 定义通用的 GET 请求方法
    public static <T> T get(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType);
        return responseEntity.getBody();
    }

    // 定义通用的 POST 请求方法
    public static  <T, R> T post(String url, R requestBody, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, requestBody, responseType);
        return responseEntity.getBody();
    }
}
