package com.verdant.demo.common.thread.basic;

/**
 * Author: verdant
 * Desc:   Runnable应用
 */
public class URunnable {
    private static class ExampleRunnable1 implements Runnable{
        @Override
        public void run() {
            System.out.println("Execute ExampleRunnable1");
        }
    }
    public static void main(String[] args) {
        //实现Runnable接口
        ExampleRunnable1 er1 = new ExampleRunnable1();
        Thread et1 = new Thread(er1);
        et1.start();

        //使用Runnable匿名内部类
        Thread et2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous inside");
            }
        });
        et2.start();
    }
}
