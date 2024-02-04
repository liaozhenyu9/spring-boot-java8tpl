package com.lzy.java8tpl.interceptor;

import com.lzy.java8tpl.util.HttpUtils;
import com.lzy.java8tpl.util.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class RestTemplateLogInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        traceRequest(request, body);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ClientHttpResponse response = execution.execute(request, body);
        stopWatch.stop();
        traceResponse(response, stopWatch.getTotalTimeMillis());
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.info(">>>>>>>> {} {}", request.getMethod(), request.getURI());
        log.info("Headers     : {}", request.getHeaders());
        log.info("Request body: {}", new String(body, "UTF-8"));
        log.info(">>>>>>>> END ({}-byte body)", body.length);
    }

    private void traceResponse(ClientHttpResponse response, long cost) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))) {
            String responseStr = bufferedReader.lines().collect(Collectors.joining("\n"));
            log.info("<<<<<<<< {} ({}ms)", response.getStatusCode(), cost);
            log.info("Headers      : {}", response.getHeaders());
            log.info("Response body: {}", !HttpUtils.isContentTypeContainingHtml(response) ? responseStr : StrUtils.truncateString(responseStr, 1000).concat("...[Content truncated due to length limit]"));
            log.info("<<<<<<<< END ({}-length body)", responseStr.length());
        }
    }
}
