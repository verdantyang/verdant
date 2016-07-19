package creational.factory.uml.factory;

import creational.factory.uml.product.AbstractProduct;
import creational.factory.uml.product.ConcreteProductA;
import creational.factory.uml.product.ConcreteProductB;

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
