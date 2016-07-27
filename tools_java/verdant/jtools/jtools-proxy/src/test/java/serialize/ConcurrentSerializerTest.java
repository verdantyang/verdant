package serialize;


import com.verdant.jtools.proxy.codec.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 序列化并发测试
 *
 * @author verdant
 * @since 2016/06/21
 */
public class ConcurrentSerializerTest {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentSerializerTest.class);
    private static final Integer QUEUE_SIZE = 1000;
    private static final Integer CONCURRENT_SIZE = 5;
    private static final Integer STR_SIZE = 200;
    private BlockingQueue<String> queue = new ArrayBlockingQueue(QUEUE_SIZE);

    public static String genString(int length) {
        String dict = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(dict.length());
            sb.append(dict.charAt(number));
        }
        return sb.toString();
    }

    public static Object genElem(Class<?> clazz) {
        if (clazz == String.class)
            return genString(STR_SIZE);
        else
            return null;
    }

    private static class Producer extends Thread {

        private BlockingQueue<String> queue;

        public Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String str = (String) genElem(String.class);
                    logger.debug("Producer msg：{}", str);
                    this.queue.put(str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Consumer implements Runnable {

        private BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String msg = this.queue.take();
                    logger.debug("result msg：{}", msg);
                    byte[] bytes = Serializer.serialize(msg);
                    String destr = Serializer.deserialize(bytes);
                    logger.info("deserialize msg {}", destr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        Producer p = new Producer(queue);
        new Thread(p).start();

        for (int i = 0; i <= CONCURRENT_SIZE; i++) {
            Consumer c = new Consumer(queue);
            new Thread(c).start();
        }
    }
}
