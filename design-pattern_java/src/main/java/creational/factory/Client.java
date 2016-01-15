package creational.factory;

import creational.factory.factory.AbstractFactory;
import creational.factory.factory.ConcreteFactoryA;
import creational.factory.factory.ConcreteFactoryB;
import creational.factory.product.AbstractProduct;

/**
 * Created by verdant on 2016/1/14.
 * Pattern: 工厂
 */
public class Client {
    public static void main(String[] args) {
        AbstractFactory concreteFactoryA = new ConcreteFactoryA();
        concreteFactoryA.createProduct();

        AbstractFactory concreteFactoryB = new ConcreteFactoryB();
        concreteFactoryB.createProduct();
    }
}
