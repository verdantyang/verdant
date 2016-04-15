package com.verdant.demo.common.thread.multi;

import java.util.concurrent.ExecutorService;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   多线程调度
 */
public class MultiSched {

    public static void main(String[] args) {
        ExecutorService fixedThreadPool = ThreadPool.getFixedThreadPool();
        ExecutorService cachedThreadPool= ThreadPool.getCachedThreadPool();
        ExecutorService scheduledThreadPool= ThreadPool.getScheduledThreadPool();
        ExecutorService singleThreadExecutor= ThreadPool.getSingleThreadExecutor();
        run(singleThreadExecutor);
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
                System.out.println(String.format(format,taskID,i));
            }
        }
    }
}
