package creational.factory.case1.factory;

import creational.factory.case1.product.BuickCar;
import creational.factory.case1.product.Car;
import creational.factory.case1.product.FordCar;

/**
 * 抽象方法
 *
 * @author verdant
 * @since 2016/07/06
 */
public enum  CarFactory2 {
    FordCarType {
        public Car create(){
            return new FordCar();
        }
    },
    BuickCarType {
        public Car create() {
            return new BuickCar();
        }
    };
    //抽象生产方法
    public abstract Car create();
}
