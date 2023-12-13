package com.lzy.java8tpl.test.algorithm.sortalgorithm;

import com.lzy.java8tpl.test.algorithm.RandomUtils;
import org.springframework.util.StopWatch;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SelectSortMain {

    public static void main(String[] args) {
        costTime();
//        sortCostTime();
    }

    public static void costTime() {
        int length = 100000;
        System.out.println("数组长度：" + length);
        int[] arr1 = RandomUtils.generateRandomArray(length);
        int[] arr2 = Arrays.copyOf(arr1, arr1.length);
        int[] arr3 = Arrays.copyOf(arr1, arr1.length);
        int[] arr4 = Arrays.copyOf(arr1, arr1.length);

        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        SortAlgorithmUtils.quickSort(arr1, 0, arr1.length - 1);
        stopWatch1.stop();
        System.out.println("快速排序耗时: " + stopWatch1.getTotalTimeMillis() + "毫秒");

        StopWatch stopWatch4 = new StopWatch();
        stopWatch4.start();
        SortAlgorithmUtils.insertSort(arr4);
        stopWatch4.stop();
        System.out.println("插入排序耗时: " + stopWatch4.getTotalTimeMillis() + "毫秒");

        StopWatch stopWatch3 = new StopWatch();
        stopWatch3.start();
        SortAlgorithmUtils.selectSort(arr3);
        stopWatch3.stop();
        System.out.println("选择排序耗时: " + stopWatch3.getTotalTimeMillis() + "毫秒");

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        SortAlgorithmUtils.bubbleSort(arr2);
        stopWatch2.stop();
        System.out.println("冒泡排序耗时: " + stopWatch2.getTotalTimeMillis() + "毫秒");

    }

    public static void sortCostTime() {
        int count = 100000;
        int length = 100;
        System.out.println("数组个数: " + count);
        System.out.println("数组长度: " + length);
        List<int[]> arrayList1 = RandomUtils.generateRandomArrayList(length, count);
        List<int[]> arrayList2 = arrayList1.stream().map(e -> Arrays.copyOf(e, e.length)).collect(Collectors.toList());
        List<int[]> arrayList3 = arrayList1.stream().map(e -> Arrays.copyOf(e, e.length)).collect(Collectors.toList());
        List<int[]> arrayList4 = arrayList1.stream().map(e -> Arrays.copyOf(e, e.length)).collect(Collectors.toList());

        StopWatch stopWatch4 = new StopWatch();
        stopWatch4.start();
        for (int i = 0; i < count; i++) {
            int[] arr = arrayList4.get(i);
            SortAlgorithmUtils.insertSort(arr);
        }
        stopWatch4.stop();
        System.out.println("插入排序耗时: " + stopWatch4.getTotalTimeMillis() + "毫秒");

        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        for (int i = 0; i < count; i++) {
            int[] arr = arrayList1.get(i);
            SortAlgorithmUtils.quickSort(arr, 0, arr.length - 1);
        }
        stopWatch1.stop();
        System.out.println("快速排序耗时: " + stopWatch1.getTotalTimeMillis() + "毫秒");

        StopWatch stopWatch3 = new StopWatch();
        stopWatch3.start();
        for (int i = 0; i < count; i++) {
            int[] arr = arrayList3.get(i);
            SortAlgorithmUtils.selectSort(arr);
        }
        stopWatch3.stop();
        System.out.println("选择排序耗时: " + stopWatch3.getTotalTimeMillis() + "毫秒");

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        for (int i = 0; i < count; i++) {
            int[] arr = arrayList2.get(i);
            SortAlgorithmUtils.bubbleSort(arr);
        }
        stopWatch2.stop();
        System.out.println("冒泡排序耗时: " + stopWatch2.getTotalTimeMillis() + "毫秒");
    }
}
