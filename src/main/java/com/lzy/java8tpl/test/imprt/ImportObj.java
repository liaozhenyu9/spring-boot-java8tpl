package com.lzy.java8tpl.test.imprt;

import com.lzy.java8tpl.test.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImportObj {

    public ImportObj() {
        log.info("new ImportObj............");
    }
    public void log() {

    }

    @Bean
    public Test test() {
        return new Test();
    }
}
