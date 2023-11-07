package com.lzy.java8tpl.test.algorithm;

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
}
