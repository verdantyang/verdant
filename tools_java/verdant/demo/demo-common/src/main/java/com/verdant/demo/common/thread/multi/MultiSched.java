package com.verdant.demo.common.thread.multi;

import java.util.concurrent.ExecutorService;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   多线程调度
 */
public class MultiSched {
    public static void main(String[] args) {
        multiThread mt = new multiThread();
        ExecutorService exec = ThreadPool.getFixedThreadPool();
        exec.execute(mt);
    }

    static class multiThread implements Runnable {

        @Override
        public void run() {
            System.out.println(1);
        }
    }
}
