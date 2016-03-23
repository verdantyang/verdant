package creational.abstractFactory;

import creational.abstractFactory.factory.AbstractFactory;
import creational.abstractFactory.factory.ConcreteFactoryA;
import creational.abstractFactory.factory.ConcreteFactoryB;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Func:   抽象工厂模式（将产品组件化）
 */
public class ClientAbstract {
    public static void main(String[] args) {
        AbstractFactory concreteFactoryA = new ConcreteFactoryA();
        concreteFactoryA.createComponent1();
        concreteFactoryA.createComponent2();

        AbstractFactory concreteFactoryB = new ConcreteFactoryB();
        concreteFactoryB.createComponent1();
        concreteFactoryB.createComponent2();
    }
}
