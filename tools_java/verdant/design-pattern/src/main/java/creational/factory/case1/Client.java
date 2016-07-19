package creational.factory.case1;

import creational.factory.case1.factory.CarFactory;
import creational.factory.case1.factory.CarFactory2;
import creational.factory.case1.product.Car;

/**
 * 使用枚举实现工厂模式
 *
 * @author verdant
 * @since 2016/07/06
 */
public class Client {
    public static void main(String[] args) {
        Car car = CarFactory.BuickCarType.create();
        Car car2 = CarFactory2.BuickCarType.create();
    }
}
