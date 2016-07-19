package creational.factory.case1.factory;

import creational.factory.case1.product.BuickCar;
import creational.factory.case1.product.Car;
import creational.factory.case1.product.FordCar;

/**
 * 非静态方法
 *
 * @author verdant
 * @since 2016/07/06
 */
public enum CarFactory {
    FordCarType, BuickCarType;
    //生产汽车
    public Car create () {
        switch (this) {
            case FordCarType:
                return new FordCar();
            case BuickCarType:
                return new BuickCar();
            default:
                throw new AssertionError("无效参数");
        }
    }
}
