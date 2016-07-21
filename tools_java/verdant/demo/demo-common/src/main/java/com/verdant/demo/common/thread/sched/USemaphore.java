package com.verdant.demo.common.thread.sched;

import java.util.concurrent.*;

/**
 * Semaphore用例
 *
 * @author verdant
 * @since 2016/07/14
 */
public class USemaphore {
    private static int SEMAPHORE_SIZE = 5;

    public void semaphoreUsage() {
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semp = new Semaphore(SEMAPHORE_SIZE);

        for (int index = 0; index < 20; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可
                        semp.acquire();
                        System.out.println("Execute: " + NO);
                        Thread.sleep((long) (Math.random() * 10000));
                        // 访问完后，释放
                        semp.release();
                        System.out.println("Execute complete, now semaphore: " + semp.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
    }

    public static void main(String[] args) {
        USemaphore usage = new USemaphore();
        usage.semaphoreUsage();
    }

}
