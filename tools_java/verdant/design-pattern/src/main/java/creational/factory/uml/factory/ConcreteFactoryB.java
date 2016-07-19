package creational.factory.uml.factory;

import creational.factory.uml.product.AbstractProduct;
import creational.factory.uml.product.ConcreteProductB;

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