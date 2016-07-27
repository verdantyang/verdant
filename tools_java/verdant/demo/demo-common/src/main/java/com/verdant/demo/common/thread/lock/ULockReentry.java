package com.verdant.demo.common.thread.lock;

/**
 * 锁重入用例
 *
 * @author verdant
 * @since 2016/07/27
 */
public class ULockReentry {
    static class Father {
        public synchronized void doSomething() {
            System.out.println("Father");
        }
    }

    public static class Child extends Father {
        public synchronized void doSomething() {
            System.out.println("child");
            super.doSomething();
        }

    }

    public static void main(String[] args) {
        new ULockReentry.Child().doSomething();
    }
}
