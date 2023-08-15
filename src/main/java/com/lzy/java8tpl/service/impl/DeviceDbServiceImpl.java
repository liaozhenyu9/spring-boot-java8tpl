package com.lzy.java8tpl.service.impl;

import com.lzy.java8tpl.entity.Device;
import com.lzy.java8tpl.mapper.DeviceMapper;
import com.lzy.java8tpl.service.IDeviceDbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备信息表 服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-08-15
 */
@Service
public class DeviceDbServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceDbService {

}
