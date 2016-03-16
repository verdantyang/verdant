package creational.abstractFactory.factory;

import creational.abstractFactory.component.*;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc: 实例化工厂A
 */
public class ConcreteFactoryA implements AbstractFactory {
    @Override
    public AbstractComponentA createComponentA() {
        return new ConcreteComponentAA();
    }

    @Override
    public AbstractComponentB createComponentB() {
        return new ConcreteComponentBA();
    }
}