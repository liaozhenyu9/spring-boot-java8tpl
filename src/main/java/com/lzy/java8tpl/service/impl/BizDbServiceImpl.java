package com.lzy.java8tpl.service.impl;

import com.lzy.java8tpl.entity.Biz;
import com.lzy.java8tpl.mapper.BizMapper;
import com.lzy.java8tpl.service.IBizDbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务类型表 服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-08-15
 */
@Service
public class BizDbServiceImpl extends ServiceImpl<BizMapper, Biz> implements IBizDbService {

}
