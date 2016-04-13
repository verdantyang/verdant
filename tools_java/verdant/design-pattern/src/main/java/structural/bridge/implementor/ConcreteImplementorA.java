package structural.bridge.implementor;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   实例化具体角色A
 */
public class ConcreteImplementorA implements Implementor {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteImplementorA.class, DesignPatternEnum.Bridge);

    @Override
    public void operationImpl() {
        logger.log("Handle implementor part by " + DebugLog.getClassName(this.getClass()));
    }
}
