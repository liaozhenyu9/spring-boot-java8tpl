package com.lzy.java8tpl.controller;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.api.ParamAssert;
import com.lzy.java8tpl.param.TestValidParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/paramValidate")
@Slf4j
@Validated
public class ParamValidateController {

    @RequestMapping("/test1")
    public void test1() {
        ParamAssert.notNull(null, "参数不能为空1");
    }

    @RequestMapping("/test2")
    public void test2() {
        Assert.notNull(null, "参数不能为空2");
    }

    @RequestMapping("/test3")
    public void test3() {
        throw new RuntimeException("其它异常");
    }

    @RequestMapping("/test4")
    public void test4(@RequestBody @Valid TestValidParam param) {
        log.info("test4..........");
    }

    @RequestMapping("/test5")
    public void test5(@RequestParam String name) {
        log.info("test5..........");
    }

    @RequestMapping("/test6")
    public String test6(@NotNull(message = "name不能为空") String name) {
        log.info("test6..........");
        return JSON.toJSONString(new TestValidParam().setName("aaaa"));
    }
}
