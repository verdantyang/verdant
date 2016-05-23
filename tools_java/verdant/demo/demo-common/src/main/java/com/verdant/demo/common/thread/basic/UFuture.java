package com.verdant.demo.common.thread.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: verdant
 * Desc:   Future应用
 */
public class UFuture {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                //do something
                System.out.println("I am task1.....");
            }
        };
        Callable<Integer> task2 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //do something
                return new Integer(100);
            }
        };
        Future<?> f1 = executor.submit(task1);
        Future<Integer> f2 = executor.submit(task2);
        StackTraceElement[] ste =Thread.currentThread().getStackTrace();
        System.out.println("Task1 is completed? " + f1.isDone());
        System.out.println("Task2 is completed? " + f2.isDone());

        System.out.println("Return value by task1: " + f1.get());
        while(f1.isDone()) {
            System.out.println("Task1 is completed!");
            break;
        }

        System.out.println("Return value by task2: " + f2.get());
    }

}