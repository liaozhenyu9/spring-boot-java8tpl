package com.lzy.java8tpl;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.api.R;
import com.lzy.java8tpl.test.enums.EnumController;
import com.lzy.java8tpl.test.enums.EnumParam;
import com.lzy.java8tpl.test.enums.TestType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SpringBootJava8tplApplication.class)  //指定SpringBoot项目的启动类
@ActiveProfiles("REL")  //指定项目使用的配置文件
@Slf4j
public class StdTests {

    @Autowired
    private EnumController enumController;

    @Test
    public void test1() {
        EnumParam testEnum = new EnumParam().setName("testEnum").setTestType(TestType.AAA);
        R r = enumController.test1(testEnum);
        log.info("res: {}", JSON.toJSONString(r));
        assertThat(r.getCode()).isEqualTo("10000");
    }

    @Test
    public void test2() {
        log.info("00000000000000000000000000000000");
    }
}
