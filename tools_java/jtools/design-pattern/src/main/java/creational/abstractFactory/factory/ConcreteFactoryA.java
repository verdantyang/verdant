package creational.abstractFactory.factory;

import creational.abstractFactory.component.*;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc: 实例化工厂A
 */
public class ConcreteFactoryA implements AbstractFactory {
    public AbstractComponentA createComponentA() {
        return new ConcreteComponentAA();
    }

    public AbstractComponentB createComponentB() {
        return new ConcreteComponentBA();
    }
}