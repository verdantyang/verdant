package structural.bridge.abstraction;

import structural.bridge.implementor.ConcreteImplementorA;
import structural.bridge.implementor.Implementor;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   修正抽象角色
 */
public class RefinedAbstractionB extends Abstraction{

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteImplementorA.class, DesignPatternEnum.Bridge);

    public RefinedAbstractionB(Implementor impl) {
        super(impl);
    }

    @Override
    public void operation() {
        logger.log("Handle abstract part by " + DebugLog.getClassName(this.getClass()));
        super.operation();
    }
}
