package com.lzy.java8tpl.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "localhost", url = "http://localhost:8080")
public interface LocalhostClient {

    @PostMapping(value = "/test/2")
    String test2(@RequestParam(required = false) Map<String, Object> map);

}
