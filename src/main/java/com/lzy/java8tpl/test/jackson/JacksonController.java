package com.lzy.java8tpl.test.jackson;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jackson")
@Slf4j
public class JacksonController {
    @PostMapping("1")
    public R test1(@RequestBody JacksonTestParam param) {
        log.info("req param:{}", JSON.toJSONString(param));

        return R.ok(param);
    }

    @PostMapping("2")
    public R test2(@RequestBody StdJacksonObj param) {
        log.info("req param:{}", JSON.toJSONString(param));
        log.info("2323232");
        return R.ok(param);
    }

}
