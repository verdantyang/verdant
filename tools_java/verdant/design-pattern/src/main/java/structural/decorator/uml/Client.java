package structural.decorator.uml;

import structural.decorator.uml.component.Component;
import structural.decorator.uml.component.ConcreteComponent;
import structural.decorator.uml.decorater.ConcreteDecoratorA;
import structural.decorator.uml.decorater.ConcreteDecoratorB;
import structural.decorator.uml.decorater.Decorator;

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
