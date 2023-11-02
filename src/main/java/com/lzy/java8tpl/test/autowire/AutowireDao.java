package com.lzy.java8tpl.test.autowire;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class AutowireDao {

    private String name = "default";

    public void test() {
        log.info("AutowireDao {} test.........", name);
    }

}
