package com.lzy.java8tpl.controller;

import com.lzy.java8tpl.api.R;
import com.lzy.java8tpl.client.JsonplaceholderClient;
import com.lzy.java8tpl.client.LocalhostClient;
import com.lzy.java8tpl.client.LotusClient;
import com.lzy.java8tpl.param.UnrecognizedPageParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("openFegin")
@Slf4j
public class OpenFeginController {

    @Autowired
    private JsonplaceholderClient jsonplaceholderClient;

    @Autowired
    private LotusClient lotusClient;

    @Autowired
    private LocalhostClient localhostClient;

    @RequestMapping("/test1")
    public R<Object> test1() {

        MDC.put("requestId", UUID.randomUUID().toString());
        log.info("test1..........");
        Object todos = jsonplaceholderClient.todos();

        return R.ok(todos);
    }

    @RequestMapping("/test2")
    public R<Object> test2() {
        MDC.put("requestId", UUID.randomUUID().toString());
        log.info("test2..........");
        lotusClient.unrecognizedPage(new UnrecognizedPageParam().setCurrent(0).setSize(10));
        return R.ok();
    }

    @RequestMapping("/test3")
    public R<Object> test3() {
        MDC.put("requestId", UUID.randomUUID().toString());
        log.info("test3..........");
        Map<String, Object> map = new HashMap<>();
        map.put("sss", "aaa");
        Object o = localhostClient.test2(map);
        return R.ok(o);
    }
}
