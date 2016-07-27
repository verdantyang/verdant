package creational.singleton;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * 单例模式（懒加载）
 * 线程安全通过同步块（同时添加了个引用计数，用以回收）
 *
 * @author verdant
 * @since 2016/07/27
 */
public class SingletonSync {

    private static final DebugLog logger = DebugLogFactory.getLogger(Singleton.class, DesignPatternEnum.Singleton);

    private static SingletonSync instance;
    private static int count = 1;

    private SingletonSync() {
    }

    /**
     * 采用double check，提高执行效率
     * @return
     */
    public static SingletonSync getInstance() {
        if (instance == null) {
            synchronized (SingletonSync.class) {
                if (instance == null)
                    instance = new SingletonSync();
                count++;
            }
        }
        return instance;
    }

    public static void destroy() {
        synchronized (SingletonSync.class) {
            if (count > 1) {
                count--;
                return;
            }
            if (instance != null)
                instance = null;
        }
    }

    public static void main(String[] args) {
        SingletonSync instance = SingletonSync.getInstance();
        SingletonSync instance2 = SingletonSync.getInstance();
        logger.log(instance == instance2 ? "Instance same" : "False");
        SingletonSync.destroy();
        SingletonSync.destroy();
        logger.log(instance == null ? "Instance destroy" : "False");
    }
}