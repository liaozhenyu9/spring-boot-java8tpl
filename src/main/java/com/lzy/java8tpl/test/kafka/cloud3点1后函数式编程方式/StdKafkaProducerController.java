package com.lzy.java8tpl.test.kafka.cloud3点1后函数式编程方式;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.api.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stdKafkaProducer")
@RequiredArgsConstructor
@Slf4j
public class StdKafkaProducerController {

    private final StreamBridge streamBridge;

    @GetMapping("producerUser")
    public R producerUser() {
        User user = new User().setId(1).setName("张三");
        log.info("send create user msg: {}", JSON.toJSONString(user));
        boolean res = streamBridge.send("producerUser-out-0", user);
        log.info("send create user res: {}", res);
        return R.ok();
    }
}
