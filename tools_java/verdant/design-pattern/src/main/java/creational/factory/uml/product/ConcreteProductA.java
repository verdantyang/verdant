package creational.factory.uml.product;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化产品A
 */
public class ConcreteProductA implements AbstractProduct {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteProductA.class, DesignPatternEnum.Factory);

    public ConcreteProductA() {
        logger.log("Create -> " + DebugLog.getClassName(this.getClass()));
    }
}
