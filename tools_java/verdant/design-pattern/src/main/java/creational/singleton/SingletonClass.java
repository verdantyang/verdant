package creational.singleton;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * 单例模式
 * 线程安全通过静态内部类（保证只在加载的时候执行一次）
 * 因为SingletonClass没有static的属性，因此并不会被初始化
 * 直到调用getInstance()的时候，会首先加载SingletonClassInstance类
 *
 * @author verdant
 * @since 2016/07/27
 */
public class SingletonClass {
    private static final DebugLog logger = DebugLogFactory.getLogger(SingletonClass.class, DesignPatternEnum.Singleton);

    private SingletonClass() {
    }

    private static class InstanceHolder {
        public static SingletonClass instance = new SingletonClass();
    }

    public static SingletonClass getInstance() {
        return InstanceHolder.instance;
    }

    public static void main(String[] args) {
        SingletonClass instance = SingletonClass.getInstance();
        SingletonClass instance2 = SingletonClass.getInstance();
        logger.log(instance == instance2 ? "Instance same" : "False");
    }
}
