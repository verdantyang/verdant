package com.verdant.demo.common.thread.comm;

import java.util.HashMap;

/**
 * ThreadLocal应用
 *
 * @author verdant
 * @since 2016/07/29
 */
public class UThreadLocal {

    static ThreadLocal<HashMap> localMap = new ThreadLocal<HashMap>() {
        @Override
        protected HashMap initialValue() {
            System.out.println(Thread.currentThread().getName() + ":initialValue");
            return new HashMap();
        }
    };


    public static class T1 implements Runnable {
        int id;

        public T1(int id) {
            this.id = id;
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + ":start");
            HashMap map = localMap.get();
            for (int i = 0; i < 10; i++) {
                map.put(i, i + id * 100);
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }
            System.out.println(Thread.currentThread().getName() + ':' + map);
        }
    }

    public void run() {
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new T1(i));
        }
        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
    }

    public static void main(String[] args) {
        UThreadLocal usage = new UThreadLocal();
        usage.run();
    }
}
