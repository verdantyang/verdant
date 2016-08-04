package com.verdant.demo.common.thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin应用
 *
 * @author verdant
 * @since 2016/07/29
 */
public class UForkJoin {

    private static class CountTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                for (int i = start; i <= end; i++)
                    sum += i;
            } else {
                int middle = (start + end) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                if (leftTask.isCompletedAbnormally())
                    System.out.println(leftTask.getException());
                if (rightTask.isCompletedAbnormally())
                    System.out.println(rightTask.getException());
                leftTask.fork();
                rightTask.fork();
                int leftResult = leftTask.join();
                int reghtResult = rightTask.join();
                sum = leftResult + reghtResult;
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 100000);
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

