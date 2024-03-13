package com.lzy.java8tpl.test.enums;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enum")
@Slf4j
public class EnumController {

    @PostMapping("1")
    public R test1(@RequestBody EnumParam param) {
        log.info("req param:{}", JSON.toJSONString(param));
        return R.ok(param);
    }

}
