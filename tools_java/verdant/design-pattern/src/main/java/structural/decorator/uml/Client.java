package structural.decorator.uml;

import structural.decorator.uml.component.Component;
import structural.decorator.uml.component.ConcreteComponent;
import structural.decorator.uml.decorater.ConcreteDecoratorA;
import structural.decorator.uml.decorater.ConcreteDecoratorB;
import structural.decorator.uml.decorater.Decorator;

/**
 * 装饰者模式
 *
 * @author verdant
 * @since 2016/04/12
 */
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component = new ConcreteDecoratorA(component);
        component = new ConcreteDecoratorB(component);
        component.operation();
    }
}
