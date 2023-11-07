package com.lzy.java8tpl.test.algorithm.sortalgorithm;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.test.algorithm.RandomUtils;
import org.springframework.util.StopWatch;

import java.util.Arrays;

public class SelectSortMain {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            int[] arr = RandomUtils.generateRandomArray(20, 100);
            int [] arr2 = Arrays.copyOf(arr, arr.length);

            Arrays.sort(arr);
            SortAlgorithmUtils.bubbleSort(arr2);
            //SortAlgorithmUtils.selectSort(arr2);
            boolean b = JSON.toJSONString(arr).equals(JSON.toJSONString(arr2));
            if (!b) {
                System.out.println("error..........");
                System.out.println(JSON.toJSONString(arr));
                System.out.println(JSON.toJSONString(arr2));
            }

        }
        stopWatch.stop();
        System.out.println("end... cost: " + stopWatch.getTotalTimeMillis());
    }
}
