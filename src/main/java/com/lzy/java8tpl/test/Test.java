package com.lzy.java8tpl.test;

import com.lzy.java8tpl.test.httpclient.IdleConnectionEvictorTest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public Test() {
        log.info("test ....................");
    }

    public static void main(String[] args) throws InterruptedException {

        IdleConnectionEvictorTest.MyThread3 thread3 = new IdleConnectionEvictorTest.MyThread3();
        thread3.start();
        System.out.println("sdfsfsfd333434");
        thread3.shutDown();
    }
}
