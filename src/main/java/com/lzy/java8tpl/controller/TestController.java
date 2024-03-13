package com.lzy.java8tpl.controller;

import com.lzy.java8tpl.api.R;
import com.lzy.java8tpl.entity.Biz;
import com.lzy.java8tpl.entity.Device;
import com.lzy.java8tpl.service.impl.DeviceDbServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "测试模块")
@RequestMapping("test")
@Slf4j
public class TestController {
    
    @Autowired
    private DeviceDbServiceImpl service;

    @RequestMapping("1")
    @ApiOperation(value = "测试接口", notes = "测试")
    public R test1() throws InterruptedException {
//        log.info("test1......");
//        Thread.sleep(5000);
        service.list();
        return R.ok(new Device());
    }

    @RequestMapping("2")
    @ApiOperation(value = "测试接口2", notes = "测试2")
    public R test2() throws InterruptedException {
        log.info("test2......");
        return R.ok(new Biz());
    }
}
