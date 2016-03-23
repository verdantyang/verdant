package creational.singleton;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/13
 * Func:   单例模式
 * Desc:   线程安全通过同步块（同时添加了个引用计数，用以回收）
 */
public class SingletonSync {

    private static final DebugLog logger = DebugLogFactory.getLogger(Singleton.class, DesignPatternEnum.Singleton);

    private static SingletonSync instance;
    private static int count = 1;

    private SingletonSync() {
    }

    public static SingletonSync getInstance() {
        synchronized (SingletonSync.class) {
            if (instance == null)
                instance = new SingletonSync();
            count++;
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