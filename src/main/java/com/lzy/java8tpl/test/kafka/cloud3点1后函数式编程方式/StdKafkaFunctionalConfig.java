package com.lzy.java8tpl.test.kafka.cloud3点1后函数式编程方式;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.util.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class StdKafkaFunctionalConfig {

    @Bean
    public Consumer<String> strConsumer() {
        return str -> {
            MDCUtils.setRequestId();
            User user = JSON.parseObject(str, User.class);
            log.info("str consumer: {}", JSON.toJSONString(user));

        };
    }

    @Bean
    public Consumer<User> userConsumer() {
        return user -> {
            MDCUtils.setRequestId();
            log.info("user consumer: {}", JSON.toJSONString(user));
        };
    }
}
