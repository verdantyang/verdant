package com.verdant.demo.common.thread.sched;

import java.util.concurrent.CountDownLatch;

/**
 * CountDown用例
 *
 * @author: verdant
 */
public class UCountDown {
    private static final int COUNT = 10;

    //启动多个线程，主线程等它们执行结束
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

    //启动多个线程，且这些线程需要等到主线程通知后才开始
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


