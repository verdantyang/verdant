package structural.bridge;

import structural.bridge.abstraction.Abstraction;
import structural.bridge.abstraction.RefinedAbstractionA;
import structural.bridge.abstraction.RefinedAbstractionB;
import structural.bridge.implementor.ConcreteImplementorA;
import structural.bridge.implementor.ConcreteImplementorB;
import structural.bridge.implementor.Implementor;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Func:   桥接模式
 */
public class Client {
    public static void main(String[] args) {
        Implementor impl = new ConcreteImplementorA();
        Abstraction abstr = new RefinedAbstractionA(impl);
        abstr.operation();

        impl = new ConcreteImplementorB();
        abstr = new RefinedAbstractionB(impl);
        abstr.operation();
    }
}
