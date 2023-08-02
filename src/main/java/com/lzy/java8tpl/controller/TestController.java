package com.lzy.java8tpl.controller;

import com.lzy.java8tpl.api.Result;
import com.lzy.java8tpl.api.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试模块")
@RequestMapping("test")
public class TestController {

    @RequestMapping("1")
    @ApiOperation(value = "测试接口", notes = "测试")
    public Result test1() {
        return ResultHelper.success();
    }
}
