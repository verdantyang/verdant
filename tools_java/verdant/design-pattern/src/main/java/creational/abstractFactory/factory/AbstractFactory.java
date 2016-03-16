package creational.abstractFactory.factory;

import creational.abstractFactory.component.AbstractComponentA;
import creational.abstractFactory.component.AbstractComponentB;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc: 抽象工厂
 */
public interface AbstractFactory {
    AbstractComponentA createComponentA();
    AbstractComponentB createComponentB();
}