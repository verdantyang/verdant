package behavior.strategy.strategy;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/3/23
 * Desc:   实例化策略1
 */
public class ConcreteStrategy1 implements Strategy {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteStrategy1.class, DesignPatternEnum.Strategy);

    @Override
    public void execute() {
        logger.log("Strategy -> execute ConcreteStrategy1");
    };
}
