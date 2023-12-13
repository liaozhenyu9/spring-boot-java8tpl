package com.lzy.java8tpl.test.algorithm;

import java.util.ArrayList;
import java.util.List;
public class RandomUtils {

    /**
     * 随机生成一个数组
     * @param maxSize 数组长度：[0, maxSize]中的整数
     * @param maxValue 值得取值范围[-maxValue, maxValue]中的整数
     * @return
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < arr.length ; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static int[] generateRandomArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * (10000 + 1)) - (int) (Math.random() * (10000 + 1));
        }
        return arr;
    }

    public static List<int[]> generateRandomArrayList(int maxSize, int maxValue, int count) {
        List<int[]> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arrayList.add(generateRandomArray(maxSize, maxValue));
        }
        return arrayList;
    }

    public static List<int[]> generateRandomArrayList(int length, int count) {
        List<int[]> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arrayList.add(generateRandomArray(length));
        }
        return arrayList;
    }
}
