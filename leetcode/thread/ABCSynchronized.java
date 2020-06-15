package thread;

/**
 * <p>文件名称：ABCSynchronized.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-08-13</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class ABCSynchronized {
    public static void main(String[] args) {
        PrintABCUsingSynchronized printABC = new PrintABCUsingSynchronized();
        new Thread(printABC::printA).start();
        new Thread(printABC::printB).start();
        new Thread(printABC::printC).start();
        printABC.start();
    }
}

class PrintABCUsingSynchronized {

    private Object a = new Object();
    private Object b = new Object();
    private Object c = new Object();

    public void printA() {
        print("A", a, b);
    }

    public void printB() {
        print("B", b, c);
    }

    public void printC() {
        print("C", c, a);
    }

    public void start() {
        synchronized (a) {
            a.notify();
        }
    }

    private void print(String name, Object current, Object next) {
        for (int i = 0; i < 10; i++) {
            synchronized (current) {
                try {
                    current.wait();
                    System.out.println(Thread.currentThread().getName() + " print " + name);

                } catch (InterruptedException e) {

                } finally {
                    synchronized (next) {
                        next.notify();
                    }
                }
            }
        }
    }
}
