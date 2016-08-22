import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/8/18.
 */
public class StaticCase {
    static boolean flag;
    static int num;
    static AtomicInteger count = new AtomicInteger();

    private static class CheckBool implements Runnable {
        @Override
        public void run() {
            while (flag) {
                if (count.incrementAndGet() < 10)
                    System.out.println(num);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Thread check = new Thread(new CheckBool());
        check.start();

        flag = true;
        TimeUnit.SECONDS.sleep(1);
        num = 42;
    }
}
