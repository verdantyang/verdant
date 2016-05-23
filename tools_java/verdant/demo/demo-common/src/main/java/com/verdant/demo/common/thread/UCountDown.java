package com.verdant.demo.common.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Author: verdant
 * Desc:   CountDown应用
 */
public class UCountDown {
    private static final int COUNT = 10;

    //当启动一个线程，需要等它执行结束
    public void waitOther() throws InterruptedException{
        final CountDownLatch completeLatch = new CountDownLatch(COUNT);
        for (int i = 0; i < COUNT; ++i) {
            Thread thread = new Thread("worker thread " + i) {
                @Override
                public void run() {
                    // do xxxx
                    completeLatch.countDown();
                }
            };
            thread.start();
        }
        completeLatch.await();
    }

    //当启动很多线程，需要这些线程等到通知后才真正开始
    public void notifyOther() {
        final CountDownLatch startLatch = new CountDownLatch(COUNT);

        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread("worker thread " + i) {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        return;
                    }
                    // do xxxx
                }
            };
            thread.start();
        }
        // do xxx
        startLatch.countDown();
    }

    public static void main(String[] args) {


    }
}


