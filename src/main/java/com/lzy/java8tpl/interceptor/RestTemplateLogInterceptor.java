package com.lzy.java8tpl.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        String responseStr = IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
        log.info("<<<<<<<< {} ({}ms)", response.getStatusCode(), cost);
        log.info("Headers      : {}", response.getHeaders());
        log.info("Response body: {}", responseStr);
        log.info("<<<<<<<< END ({}-byte body)", responseStr.getBytes(StandardCharsets.UTF_8).length);
    }
}
