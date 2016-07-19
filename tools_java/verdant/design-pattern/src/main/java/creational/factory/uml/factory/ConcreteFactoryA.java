package creational.factory.uml.factory;


import creational.factory.uml.product.AbstractProduct;
import creational.factory.uml.product.ConcreteProductA;

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