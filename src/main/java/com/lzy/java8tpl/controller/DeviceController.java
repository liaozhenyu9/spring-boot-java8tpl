package com.lzy.java8tpl.controller;

import com.lzy.java8tpl.api.R;
import com.lzy.java8tpl.api.RHelper;
import com.lzy.java8tpl.entity.Device;
import com.lzy.java8tpl.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 设备信息表 前端控制器
 * </p>
 *
 * @author abc
 * @since 2023-08-14
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("1")
    public R test2() {
        List<Device> list = deviceService.list();
        return RHelper.success(list);
    }

}
