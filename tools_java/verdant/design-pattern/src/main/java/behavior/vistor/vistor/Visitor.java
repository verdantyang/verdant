package behavior.vistor.vistor;

import behavior.vistor.element.ConcreteElementA;
import behavior.vistor.element.ConcreteElementB;

/**
 * Author: verdant
 * Create: 2016/4/7
 * Desc:   抽象访问者
 */
public interface Visitor {
    void visit(ConcreteElementA el1);
    void visit(ConcreteElementB el2);
}