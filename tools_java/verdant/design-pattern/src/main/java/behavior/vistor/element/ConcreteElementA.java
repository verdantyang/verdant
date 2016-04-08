package behavior.vistor.element;

import behavior.vistor.vistor.Visitor;

/**
 * Author: verdant
 * Create: 2016/4/7
 * Desc:   实例化元素A
 */
public class ConcreteElementA extends Element{
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void doSomething() {
        super.doSomething();
    }
}
