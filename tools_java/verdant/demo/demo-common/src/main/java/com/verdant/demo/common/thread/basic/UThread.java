package com.verdant.demo.common.thread.basic;

/**
 * Thread应用
 *
 * @author verdant
 * @since 2016/07/14
 */
public class UThread {

    private static class ExampleThread1 extends Thread {
        @Override
        public void run() {
            System.out.println("Execute ExampleThread1");
        }
    }

    /**
     * 安全终止的线程
     */
    class SafeStopThread extends Thread {
        //此变量必须加上volatile
        private volatile boolean stop = false;

        @Override
        public void run() {
            //判断线程体是否运行
            while (stop) {
                // Do Something
            }
        }

        //线程终止
        public void terminate() {
            stop = true;
        }
    }

    public static void main(String[] args) {

        ExampleThread1 et1 = new ExampleThread1();
        et1.start();

        // Thread 匿名内部类
        Thread et2 = new Thread() {
            @Override
            public void run() {
                System.out.println("Anonymous inside thread");
            }
        };
        et2.start();
    }
}
