package creational.factory.factory;


import creational.factory.product.AbstractProduct;
import creational.factory.product.ConcreteProductA;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化工厂A
 */
public class ConcreteFactoryA implements AbstractFactory {
    public AbstractProduct createProduct() {
        return new ConcreteProductA();
    }
}