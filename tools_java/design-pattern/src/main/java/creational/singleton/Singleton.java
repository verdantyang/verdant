package creational.singleton;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Created by verdant on 2016/1/13.
 * 线程安全：静态内部类（保证只在加载的时候执行一次）
 */
public class Singleton {

    private Singleton() {
    }

    private static class Initializer {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Initializer.INSTANCE;
    }

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        DebugLog.print(instance == instance2 ? "Instance same" : "False",
                DesigiPatternEnum.Prototype, Singleton.class);
    }
}