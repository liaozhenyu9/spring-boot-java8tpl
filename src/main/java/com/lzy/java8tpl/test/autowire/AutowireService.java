package com.lzy.java8tpl.test.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutowireService {

    @Autowired
    private AutowireDao autowireDao;

    public void test() {
        autowireDao.test();
    }
}
