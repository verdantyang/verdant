package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>文件名称：ABCLock.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-08-12</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class ABCLock {
    public static void main(String[] args) {
        PrintABCUsingReentrantLock printABC = new PrintABCUsingReentrantLock();
        new Thread(printABC::printA).start();
        new Thread(printABC::printB).start();
        new Thread(printABC::printC).start();
    }
}

class PrintABCUsingReentrantLock {
    private Lock lock = new ReentrantLock();
    private int state = 0;

    //private int attempts =0;

    public void printA() {
        print("A", 0);
    }

    public void printB() {
        print("B", 1);
    }

    public void printC() {
        print("C", 2);
    }

    private void print(String name, int currentState) {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                while (state % 3 == currentState) {
                    System.out.println(Thread.currentThread().getName() + " print " + name);
                    state++;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
