package behavior.strategy.strategy;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/3/23
 * Desc:   实例化策略3
 */
public class ConcreteStrategy3 implements Strategy {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteStrategy3.class, DesignPatternEnum.Strategy);

    @Override
    public void execute() {
        logger.log("Strategy -> execute ConcreteStrategy3");
    };
}
