package com.verdant.demo.common.thread.multi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   Executors线程池
 */
public class ThreadPool {

    private static final int PROCESSOR_NUMS = Runtime.getRuntime().availableProcessors();
    private static final int POOL_SIZE = 1;

    // 创建可以容纳THREAD_NUMS个线程的线程池
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(PROCESSOR_NUMS * POOL_SIZE);

    // 线程池的大小会根据执行的任务数动态分配
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    // 创建单个线程的线程池，如果当前线程在执行任务时突然中断，则会创建一个新的线程替代它继续执行任务
    private static  ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    // 效果类似于Timer定时器
    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

    public static ExecutorService getFixedThreadPool() {
        return fixedThreadPool;
    }
    public static ExecutorService getCachedThreadPool() {
        return cachedThreadPool;
    }
    public static ExecutorService getSingleThreadExecutor() {
        return singleThreadPool;
    }
    public static ExecutorService getScheduledThreadPool() {
        return scheduledThreadPool;
    }

}
