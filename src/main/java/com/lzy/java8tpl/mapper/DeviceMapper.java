package com.lzy.java8tpl.mapper;

import com.lzy.java8tpl.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 设备信息表 Mapper 接口
 * </p>
 *
 * @author abc
 * @since 2023-08-14
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

}
