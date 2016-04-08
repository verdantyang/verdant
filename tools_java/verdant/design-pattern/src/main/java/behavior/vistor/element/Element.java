package behavior.vistor.element;

import behavior.vistor.vistor.Visitor;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/7
 * Desc:   抽象元素
 */
public abstract class Element {
    private static final DebugLog logger = DebugLogFactory.getLogger(Element.class, DesignPatternEnum.Visitor);

    public abstract void accept(Visitor visitor);

    public void doSomething() {
        logger.log("Visit" + DebugLog.getClassName(this.getClass()));
    }
}
