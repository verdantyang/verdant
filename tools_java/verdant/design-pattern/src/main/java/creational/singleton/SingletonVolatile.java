package creational.singleton;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

import java.io.*;

/**
 * 单例模式（懒加载）
 * 线程安全通过同步块（同时添加了个引用计数，用以回收）
 *
 * @author verdant
 * @since 2016/07/27
 */
public class SingletonVolatile implements Serializable {

    private static final DebugLog logger = DebugLogFactory.getLogger(SingletonVolatile.class, DesignPatternEnum.Singleton);

    private volatile static SingletonVolatile instance;
    private static int count = 1;

    private SingletonVolatile() {
    }

    /**
     * 采用double check，提高执行效率
     *
     * @return
     */
    public static SingletonVolatile getInstance() {
        if (instance == null) {
            synchronized (SingletonVolatile.class) {
                if (instance == null)
                    instance = new SingletonVolatile();
                count++;
            }
        }
        return instance;
    }

    /**
     * 防止单例对象在序列化后生成“多例”
     *
     * @return
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static void destroy() {
        synchronized (SingletonVolatile.class) {
            if (count > 1) {
                count--;
                return;
            }
            if (instance != null)
                instance = null;
        }
    }

    public static void main(String[] args) throws Exception {
        SingletonVolatile instance = SingletonVolatile.getInstance();
        SingletonVolatile instance2 = SingletonVolatile.getInstance();
        logger.log(instance == instance2 ? "Instance same" : "False");
        SingletonVolatile.destroy();
        SingletonVolatile.destroy();
        logger.log(instance == null ? "Instance destroy" : "False");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(SingletonVolatile.getInstance());
        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        SingletonVolatile newInstance = (SingletonVolatile) ois.readObject();
        logger.log("Serialized same: " + (newInstance == SingletonVolatile.getInstance()));
    }
}