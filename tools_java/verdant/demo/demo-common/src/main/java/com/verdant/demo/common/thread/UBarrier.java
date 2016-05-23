package com.verdant.demo.common.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Author: verdant
 * Desc:   Barrier应用
 */
public class UBarrier {
    private int threadCount;
    private CyclicBarrier barrier;
    private int loopCount=10;

    public void BarrierTest(int threadCount) throws Exception{
        this.threadCount = threadCount;
        barrier = new CyclicBarrier(threadCount, new Runnable() {
            @Override
            public void run() {
                collectTestResult();
            }
        });
        for (int i = 0; i < threadCount; ++i) {
            Thread thread = new Thread("test-thread " + i) {
                @Override
                public void run() {
                    for (int j = 0; j < loopCount; ++j) {
                        doTest();
                        try {
                            barrier.await();
                        } catch (InterruptedException |BrokenBarrierException e) {
                            return;
                        }
                    }
                }
            } ;
            thread.start();
        }
    }

    private void doTest() { /* do xxx */}

    private void collectTestResult() { /* do xxx */ }
}
