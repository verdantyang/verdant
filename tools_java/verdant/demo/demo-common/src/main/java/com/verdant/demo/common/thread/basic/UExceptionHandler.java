package com.verdant.demo.common.thread.basic;

/**
 * ExceptionHandler用例
 *
 * @author verdant
 * @since 2016/07/29
 */
public class UExceptionHandler implements Runnable {
    public UExceptionHandler() {
        Thread t = new Thread(this);
        t.setUncaughtExceptionHandler(new UUncaughtExceptionHandler());
        t.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("System run normally：" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Throw exception
        throw new RuntimeException();
    }

    private static class UUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Thread " + t.getName() + "get an exception, and restarted automaticly!");
            e.printStackTrace();
            new UExceptionHandler();
        }
    }

    public static void main(String[] args) {
        new UExceptionHandler();
    }
}