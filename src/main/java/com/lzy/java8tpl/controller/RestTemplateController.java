package com.lzy.java8tpl.controller;

import com.lzy.java8tpl.util.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

@RestController
@RequestMapping("rest")
@Slf4j
public class RestTemplateController {

    private int time = 20;

    @RequestMapping("test1")
    public String test() {

        StopWatch stopWatch = new StopWatch();
        long total = 0;
        for(int i = 0; i < time; i++) {
            stopWatch.start();
            RestTemplateUtils.get("https://jsonplaceholder.typicode.com", String.class);
            stopWatch.stop();
            long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
            total += lastTaskTimeMillis;
            log.info("test1 cost:{}ms", stopWatch.getLastTaskTimeMillis());
        }
        log.info("平均耗时：{}ms", total/time);
        return "test1";
    }

    @RequestMapping("test2")
    public String test2() {
        RestTemplate restTemplate = new RestTemplate();
        StopWatch stopWatch = new StopWatch();
        long total = 0;
        for(int i = 0; i < time; i++) {
            stopWatch.start();
            ResponseEntity<String> forEntity = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos/1", String.class);
            stopWatch.stop();
            long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
            total += lastTaskTimeMillis;
            log.info("test2 cost:{}ms", stopWatch.getLastTaskTimeMillis());
        }
        log.info("平均耗时：{}ms", total/time);
        return "test2";
    }

    @RequestMapping("test3")
    public String test3() {
        HashMap<String, String> header = new HashMap<>();
        header.put("X-header1", "sss");
        header.put("X-header2", "2222");

        ParameterizedTypeReference<String> typeReference = new ParameterizedTypeReference<String>(){};
        ResponseEntity<String> stringResponseEntity = RestTemplateUtils.post("http://localhost:8090/test/2", null, typeReference);
        String body = stringResponseEntity.getBody();
        return body;
    }

    @RequestMapping("test4")
    public String test4() {
        RestTemplateUtils.get("http://localhost:8090/test/2", String.class);
        return "test4";
    }
}
