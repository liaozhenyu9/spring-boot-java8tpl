package com.lzy.java8tpl.test.algorithm.sortalgorithm;

import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.test.algorithm.RandomUtils;
import org.springframework.util.StopWatch;
import java.util.Arrays;

public class SelectSortMain {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();

        for (int i = 0; i < 10; i++) {
            int[] arr = RandomUtils.generateRandomArray(20, 100);
            int [] arr2 = Arrays.copyOf(arr, arr.length);
            stopWatch.start();

            Arrays.sort(arr);
//            SortAlgorithmUtils.bubbleSort(arr2);
            stopWatch.stop();
            //SortAlgorithmUtils.bubbleSort(arr2);
            boolean b = JSON.toJSONString(arr).equals(JSON.toJSONString(arr2));
            if (!b) {
                System.out.println("error..........");
                System.out.println(JSON.toJSONString(arr));
                System.out.println(JSON.toJSONString(arr2));
            }

        }
        System.out.println("end... cost: " + stopWatch.getTotalTimeMillis());
    }
}
