package creational.abstractFactory.factory;

import creational.abstractFactory.component.*;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc: 实例化工厂B
 */
public class ConcreteFactoryB implements AbstractFactory {
    public AbstractComponentA createComponentA() {
        return new ConcreteComponentAB();
    }

    public AbstractComponentB createComponentB() {
        return new ConcreteComponentBB();
    }
}