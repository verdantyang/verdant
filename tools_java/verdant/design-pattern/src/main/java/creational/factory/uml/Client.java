package creational.factory.uml;

import creational.factory.uml.factory.AbstractFactory;
import creational.factory.uml.factory.ConcreteFactoryA;
import creational.factory.uml.factory.ConcreteFactoryB;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Func:   工厂模式
 */
public class Client {
    public static void main(String[] args) {
        AbstractFactory concreteFactoryA = new ConcreteFactoryA();
        concreteFactoryA.createProduct();

        AbstractFactory concreteFactoryB = new ConcreteFactoryB();
        concreteFactoryB.createProduct();
    }
}
