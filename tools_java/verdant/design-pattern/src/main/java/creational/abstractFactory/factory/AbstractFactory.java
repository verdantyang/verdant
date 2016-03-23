package creational.abstractFactory.factory;

import creational.abstractFactory.component.AbstractComponent1;
import creational.abstractFactory.component.AbstractComponent2;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc: 抽象工厂
 */
public interface AbstractFactory {
    AbstractComponent1 createComponent1();
    AbstractComponent2 createComponent2();
}