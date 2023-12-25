package com.lzy.java8tpl.test.kafka;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.util.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class KafkaFunctionalConfig {


    /**
     * 生产User对象
     */
//    @Bean
//    public Supplier<User> produceUser() {
//        return () -> {
//            log.info("发送消息");
//            return new User();
//        };
//    }

    /**
     * 加工用户信息
     * @return
     */
//    @Bean
//    public Function<User, User> processUser() {
//        return user -> {
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String nowStr = now.format(formatter);
//            user.setId(now.getSecond());
//            user.setName(nowStr);
//            return user;
//        };
//    }

    /**
     * 消费用户信息
     * @return
     */
//    @Bean
//    public Consumer<User> consumerUser() {
//        return user -> {
//          log.info(user.briefIntroduction());
//        };
//    }

    @Bean
    public Consumer<String> consumer1() {
        return str -> {
            MDCUtils.setRequestId();
            log.info("consume1:{} + time:{}", str.toUpperCase(), JSON.toJSONString(LocalDateTime.now()));
            throw new RuntimeException();
        };
    }

    @Bean
    public Consumer<String> consumer2() {
        return str -> {
            log.info("consume2:" + str.toUpperCase());
        };
    }
}
