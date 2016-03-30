package behavior.strategy.strategy;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/3/23
 * Desc:   实例化策略2
 */
public class ConcreteStrategy2 implements Strategy {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteStrategy2.class, DesignPatternEnum.Strategy);

    @Override
    public void execute() {
        logger.log("Execute -> " + DebugLog.getClassName(this.getClass()));
    };
}
