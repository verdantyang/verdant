package com.verdant.demo.common.thread.multi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   多线程调度
 */
public class UThreadPool {

    private static final int PROCESSOR_NUMS = Runtime.getRuntime().availableProcessors();
    private static final int POOL_SIZE = 1;

    // 创建可以容纳THREAD_NUMS个线程的线程池
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(PROCESSOR_NUMS * POOL_SIZE);

    // 线程池的大小会根据执行的任务数动态分配
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    // 创建单个线程的线程池，如果当前线程在执行任务时突然中断，则会创建一个新的线程替代它继续执行任务
    private static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    // 效果类似于Timer定时器
    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

    public static void main(String[] args) {
        run(singleThreadPool);
    }

    private static void run(ExecutorService threadPool) {
        for (int i = 1; i < 5; i++) {
            final int taskID = i;
            threadPool.execute(new multiThread(taskID));
        }
        threadPool.shutdown();// 任务执行完毕，关闭线程池
    }

    static class multiThread implements Runnable {
        private Integer taskID;
        private final String format = "TaskID(%d): Execute times %d";

        public multiThread(Integer taskID) {
            this.taskID = taskID;
        }

        @Override
        public void run() {
            for (int i = 1; i < 5; i++) {
                try {
                    Thread.sleep(20);// 为了测试出效果，让每次任务执行都需要一定时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format(format, taskID, i));
            }
        }
    }
}
