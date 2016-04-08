package behavior.vistor.vistor;

import behavior.vistor.element.ConcreteElementA;
import behavior.vistor.element.ConcreteElementB;

/**
 * Author: verdant
 * Create: 2016/4/7
 * Desc:   实例化访问者
 */
public class ConcreteVisitor implements Visitor{
    @Override
    public void visit(ConcreteElementA el1) {
        el1.doSomething();
    }

    @Override
    public void visit(ConcreteElementB el2) {
        el2.doSomething();
    }
}
