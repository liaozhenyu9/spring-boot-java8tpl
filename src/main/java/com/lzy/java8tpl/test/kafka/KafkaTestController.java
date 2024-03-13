package com.lzy.java8tpl.test.kafka;

import com.lzy.java8tpl.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafkaTest")
public class KafkaTestController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("1")
    public R test1() {
        streamBridge.send("supplier1-out-0", "aaaaaaaaaaaaaaaaaaaaa");

        streamBridge.send("supplier2-out-0", "bbbbbbbbbbbbbbbbbbbbb");
        return R.ok();
    }
}
