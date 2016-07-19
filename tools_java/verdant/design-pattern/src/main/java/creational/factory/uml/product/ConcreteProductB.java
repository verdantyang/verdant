package creational.factory.uml.product;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化产品B
 */
public class ConcreteProductB implements AbstractProduct{

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteProductB.class, DesignPatternEnum.Factory);

    public ConcreteProductB() {
        logger.log("Create -> " + DebugLog.getClassName(this.getClass()));
    }
}
