package thread;

import java.util.concurrent.Semaphore;

/**
 * <p>文件名称：ABCSemphore.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-08-12</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class ABCSemphore {
    public static void main(String[] args) {
        PrintABCUsingSemaphore printABC = new PrintABCUsingSemaphore();
        new Thread(printABC::printA).start();
        new Thread(printABC::printB).start();
        new Thread(printABC::printC).start();
    }
}

class PrintABCUsingSemaphore {
    private Semaphore semaphoreA = new Semaphore(1);
    private Semaphore semaphoreB = new Semaphore(0);
    private Semaphore semaphoreC = new Semaphore(0);
    //private int attempts = 0;


    public void printA() {
        print("A", semaphoreA, semaphoreB);
    }

    public void printB() {
        print("B", semaphoreB, semaphoreC);
    }

    public void printC() {
        print("C", semaphoreC, semaphoreA);
    }

    private void print(String name, Semaphore current, Semaphore next) {
        for (int i = 0; i < 10; i++) {
            try {
                current.acquire();
                System.out.println(Thread.currentThread().getName() + " print " + name);
                next.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}