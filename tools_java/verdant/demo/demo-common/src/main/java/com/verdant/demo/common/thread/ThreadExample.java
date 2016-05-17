package com.verdant.demo.common.thread;

/**
 * Author: verdant
 * Desc:   Thread应用
 */
public class ThreadExample {

    private static class ExampleThread1 extends Thread{
        @Override
        public void run() {
            System.out.println("Execute ExampleThread1");
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
