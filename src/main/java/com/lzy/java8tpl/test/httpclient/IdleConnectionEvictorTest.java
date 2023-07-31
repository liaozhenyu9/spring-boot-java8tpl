package com.lzy.java8tpl.test.httpclient;

import com.lzy.java8tpl.param.TestValidParam;

public class IdleConnectionEvictorTest {

    public static void main(String[] args) throws InterruptedException {

        //测试notify()唤醒阻塞线程后，继续执行阻塞线程中wait()方法后面的代码
        MyThread3 thread3 = new MyThread3();
        thread3.start();
        Thread.sleep(1000);
        thread3.shutDown();
    }

    public static class MyThread3 extends Thread {

        private volatile boolean shutDownFlag = false;

        public void run() {

            synchronized (this) {
                System.out.println("run.....");
                int i = 0;

                while (!shutDownFlag) {
                    try {
                        this.wait(5000);
                        System.out.println("MyThread3 run" + i++);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        }

        public void shutDown() {
            shutDownFlag = true;
            synchronized (this) {
                System.out.println("shut down");
                this.notifyAll();
            }
        }
    }


    public static class MyThread1 extends Thread {
        private TestValidParam t;

        public MyThread1(TestValidParam t) {
            this.t = t;
        }

        public void run() {

            synchronized(t) {

                try {
                    t.wait(1000 * 5);
                    System.out.println("thread1...........end");

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

    public static class MyThread2 implements Runnable {

        private TestValidParam t;

        public MyThread2(TestValidParam t) {
            this.t = t;
        }

        @Override
        public void run() {
            synchronized(t) {
                try {
                    t.wait(1000 * 6);
                    System.out.println("thread2...........end");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);

                }
            }
        }
    }


}


