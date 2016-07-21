package com.verdant.demo.common.thread.sched;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Barrier用例
 *
 * @author verdant
 * @since 2016/07/14
 */
public class UBarrier {
    private static int threadCount = 10;
    private CyclicBarrier barrier;
    private int loopCount = 10;

    //等待threadCount个子任务执行完后，才执行主任务
    public void barrierUsage() {
        barrier = new CyclicBarrier(threadCount, new Runnable() {
            @Override
            public void run() {
                System.out.println("--Execute main task!");
            }
        });
        for (int i = 0; i < threadCount; ++i) {
            Thread thread = new Thread("test-thread " + i) {
                @Override
                public void run() {
                    for (int j = 0; j < loopCount; ++j) {
                        /* do xxx */
                        System.out.println("Execute subtask!");
                        try {
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            return;
                        }
                    }
                }
            };
            thread.start();
        }
    }

    public static void main(String[] args) {
        UBarrier usage = new UBarrier();
        usage.barrierUsage();
    }
}
