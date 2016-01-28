package creational.factory.factory;

import creational.factory.product.AbstractProduct;
import creational.factory.product.ConcreteProductB;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化工厂B
 */
public class ConcreteFactoryB implements AbstractFactory {
    public AbstractProduct createProduct() {
        return new ConcreteProductB();
    }
}