package thread;

/**
 * <p>文件名称：ABCVolatile.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-08-12</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class ABCVolatile {

    public static void main(String[] args) {
        PrintABCUsingVolatile printABC = new PrintABCUsingVolatile();
        new Thread(printABC::printA).start();
        new Thread(printABC::printB).start();
        new Thread(printABC::printC).start();
    }
}

class PrintABCUsingVolatile {
    private volatile int status = 0;

    public void printA() {
        print("A", 0, 1);
    }

    public void printB() {
        print("B", 1, 2);
    }

    public void printC() {
        print("C", 2, 0);
    }

    private void print(String name, int current, int next) {
        for (int i = 0; i < 10; i++) {
            while (status != current) {
            }

            System.out.print(name);
            status = next;
        }
    }
}
