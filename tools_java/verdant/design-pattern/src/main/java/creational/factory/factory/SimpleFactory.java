package creational.factory.factory;

import creational.factory.product.AbstractProduct;
import creational.factory.product.ConcreteProductA;
import creational.factory.product.ConcreteProductB;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   简单工厂
 */
public class SimpleFactory {
    public AbstractProduct createProduct(int type) {
        switch (type) {
            case 'A':
                return new ConcreteProductA();

            case 'B':
                return new ConcreteProductB();

            default:
                break;
        }
        return null;
    }
}
