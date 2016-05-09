package structural.adapter.adapterTarget;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   实例化目标接口
 */
public class ConcreteTarget implements Target {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteTarget.class, DesignPatternEnum.Adapter);

    @Override
    public void request() {
        logger.log("Handle by " + DebugLog.getClassName(this.getClass()));
    }
}
