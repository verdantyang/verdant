package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>文件名称：ABCCondition.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-08-12</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class ABCCondition {
    public static void main(String[] args) {
        PrintABCUsingCondition printABC = new PrintABCUsingCondition();
        new Thread(printABC::printA).start();
        new Thread(printABC::printB).start();
        new Thread(printABC::printC).start();
        printABC.start();
    }
}

class PrintABCUsingCondition {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void printA() {
        print("A", conditionA, conditionB);
    }

    public void printB() {
        print("B", conditionB, conditionC);
    }

    public void printC() {
        print("C", conditionC, conditionA);
    }

    public void start() {
        lock.lock();
        try {
            conditionA.signal();
        } finally {
            lock.unlock();
        }
    }

    private void print(String name, Condition currentCondition, Condition nextCondition) {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                currentCondition.await();
                System.out.println(Thread.currentThread().getName() + " print " + name);
                nextCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
