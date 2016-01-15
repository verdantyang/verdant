package creational.abstractFactory;

import creational.abstractFactory.factory.AbstractFactory;
import creational.abstractFactory.factory.ConcreteFactoryA;
import creational.abstractFactory.factory.ConcreteFactoryB;

/**
 * Created by verdant on 2016/1/14.
 * Pattern: 抽象工厂（将产品组件化）
 */
public class ClientAbstract {
    public static void main(String[] args) {
        AbstractFactory concreteFactoryA = new ConcreteFactoryA();
        concreteFactoryA.createComponentA();
        concreteFactoryA.createComponentB();

        AbstractFactory concreteFactoryB = new ConcreteFactoryB();
        concreteFactoryB.createComponentA();
        concreteFactoryB.createComponentB();
    }
}
