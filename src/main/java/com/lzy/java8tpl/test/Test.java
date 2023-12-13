package com.lzy.java8tpl.test;

import com.lzy.java8tpl.test.algorithm.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@Slf4j
public class Test {
    public Test() {
        log.info("test ....................");
    }

    public static void main(String[] args) throws InterruptedException {

        int a = -2100000002;
        System.out.println(a);
        printB(a);

        for (int i = 1; i <= 4; i++) {
            int b = a >> i;
            System.out.println(a + "右移"+ i + "位 = " + b);
            printB(b);
        }
    }

    public static void printB(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }

    public static void cost() {
        int length = 10000000;
        System.out.println("数组长度：" + length);

        int[] arr = RandomUtils.generateRandomArray(length);

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        for (int i = 0; i < arr.length; i++) {
            int a = arr[i] & 1;
        }
        stopWatch2.stop();
        System.out.println("&1 耗时" + stopWatch2.getTotalTimeMillis() + "毫秒");

//        StopWatch stopWatch1 = new StopWatch();
//        stopWatch1.start();
//        for (int i = 0; i < arr.length; i++) {
//            int a = arr[i] % 2;
//        }
//        stopWatch1.stop();
//        System.out.println("%2 耗时" + stopWatch1.getTotalTimeMillis() + "毫秒");


    }
}
