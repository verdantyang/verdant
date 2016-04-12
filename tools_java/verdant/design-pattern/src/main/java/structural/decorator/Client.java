package structural.decorator;

import structural.decorator.component.Component;
import structural.decorator.component.ConcreteComponent;
import structural.decorator.decorater.ConcreteDecoratorA;
import structural.decorator.decorater.ConcreteDecoratorB;
import structural.decorator.decorater.Decorator;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Func:   装饰者模式
 */
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        Decorator cda = new ConcreteDecoratorA(component);
        Decorator cdb = new ConcreteDecoratorB(cda);
        cdb.operation();
    }
}
