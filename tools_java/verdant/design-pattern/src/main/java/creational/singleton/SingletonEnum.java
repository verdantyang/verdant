package creational.singleton;

/**
 * 单例模式（枚举）
 *
 * @author verdant
 * @since 2016/07/27
 */
public class SingletonEnum {

    private SingletonEnum() {

    }

    public static SingletonEnum getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private SingletonEnum instance;

        Singleton() {
            instance = new SingletonEnum();
        }

        public SingletonEnum getInstance() {
            return instance;
        }
    }
}
