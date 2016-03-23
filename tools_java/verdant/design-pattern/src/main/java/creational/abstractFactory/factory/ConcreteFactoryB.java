package creational.abstractFactory.factory;

import creational.abstractFactory.component.*;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc: 实例化工厂B
 */
public class ConcreteFactoryB implements AbstractFactory {
    @Override
    public AbstractComponent1 createComponent1() {
        return new ConcreteComponentB1();
    }

    @Override
    public AbstractComponent2 createComponent2() {
        return new ConcreteComponentB2();
    }
}