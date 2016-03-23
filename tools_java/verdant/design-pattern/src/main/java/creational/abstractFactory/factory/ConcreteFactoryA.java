package creational.abstractFactory.factory;

import creational.abstractFactory.component.*;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc: 实例化工厂A
 */
public class ConcreteFactoryA implements AbstractFactory {
    @Override
    public AbstractComponent1 createComponent1() {
        return new ConcreteComponentA1();
    }

    @Override
    public AbstractComponent2 createComponent2() {
        return new ConcreteComponentA2();
    }
}