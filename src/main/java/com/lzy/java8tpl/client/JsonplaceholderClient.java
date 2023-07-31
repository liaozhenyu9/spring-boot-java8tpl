package com.lzy.java8tpl.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "jsonplaceholder", url = "${jsonplaceholder.url}")
public interface JsonplaceholderClient {
    @RequestMapping(method = RequestMethod.GET, value = "todos/1")
    Object todos();
}
