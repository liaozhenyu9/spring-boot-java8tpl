package com.lzy.java8tpl.controller;

import com.lzy.java8tpl.api.R;
import com.lzy.java8tpl.api.RHelper;
import com.lzy.java8tpl.param.JsonAliasParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sign")
@Validated
public class SignInterceptorController {

    @RequestMapping("1")
    public R test1() {

        return RHelper.success();
    }

    @RequestMapping("2")
    public R test2(@RequestAttribute(name = "biz_content") JsonAliasParam param) {

        return RHelper.success();
    }
}
