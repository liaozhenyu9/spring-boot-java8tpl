package com.lzy.java8tpl.test.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 CountDownLatch 是Java中的一个并发工具类，用于在多线程场景中控制线程的同步执行。它的作用是允许一个或多个线程等待其他线程完成一组操作，然后再继续执行。
 CountDownLatch 的工作原理很简单：它维护了一个计数器，当计数器的值达到零时，等待中的线程就会被唤醒。在创建 CountDownLatch 实例时，需要指定一个初始的计数值，
 然后在需要等待的线程中调用 await() 方法进行等待，直到计数值为零。在其他线程完成任务后，可以调用 countDown() 方法来减少计数值，从而最终将计数值减至零，唤醒所有等待的线程继续执行。
 CountDownLatch 主要用于以下场景：
 等待多个任务完成： 当有多个任务需要在某个特定点上同步，等待所有任务完成后再继续执行，可以使用 CountDownLatch。
 主线程等待子线程： 在主线程中创建多个子线程，主线程需要等待所有子线程完成后再继续执行，可以使用 CountDownLatch 来控制等待。
 */
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                // 模拟任务的执行
                System.out.println("Thread " + Thread.currentThread().getId() + " started");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread " + Thread.currentThread().getId() + " completed");
                latch.countDown();
            });
            thread.start();
        }

        System.out.println("Waiting for threads to complete...");
        latch.await(); // 等待所有线程完成
        System.out.println("All threads have completed.");
    }
}
