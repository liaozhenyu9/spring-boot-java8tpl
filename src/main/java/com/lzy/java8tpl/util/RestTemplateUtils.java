package com.lzy.java8tpl.util;

import com.lzy.java8tpl.api.RemoteErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
public class RestTemplateUtils {

    private static RestTemplate restTemplate;

    @Resource
    public void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateUtils.restTemplate = restTemplate;
    }


    public static <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        try {
            return restTemplate.getForEntity(url, responseType);
        } catch (Exception e) {
            handleException(url, e);
            return null;
        }
    }

    public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType) {
        try {
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        } catch (Exception e) {
            handleException(url, e);
            return null;
        }
    }

    public static <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return get(url, httpHeaders, responseType);
    }

    public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.GET, headers, null, responseType);
    }

    public static <T> ResponseEntity<T> get(String url, Map<String, String> headers, ParameterizedTypeReference<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return get(url, httpHeaders, responseType);
    }

    public static <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
        return get(url, (HttpHeaders) null, responseType);
    }

    public static <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType) {
        try {
            return restTemplate.postForEntity(url, request, responseType);
        } catch (Exception e) {
            handleException(url, e);
            return null;
        }
    }

    public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object request, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(request, headers);
        return post(url, requestEntity, responseType);
    }

    public static <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object request, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, request, responseType);
    }


    public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object request, ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.POST, headers, request, responseType);
    }

    public static <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object request, ParameterizedTypeReference<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, request, responseType);
    }

    public static <T> ResponseEntity<T> post(String url, Object request, ParameterizedTypeReference<T> responseType) {
        return post(url, (HttpHeaders) null, request, responseType);
    }

    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpHeaders headers, Object request, ParameterizedTypeReference<T> responseType) {
        try {
            HttpEntity<?> requestEntity = new HttpEntity<>(request, headers);
            return restTemplate.exchange(url, method, requestEntity, responseType);
        } catch (Exception e) {
            handleException(url, e);
            return null;
        }
    }

    public static void handleException(String url, Exception e) {
        log.error("remote service invoke failed, url:{}", url);
        log.error("exception type                   :{}", e.getClass());
        log.error("exception reason                 :{}", e.getMessage());
        throw new RemoteErrorException(e);
    }

}
