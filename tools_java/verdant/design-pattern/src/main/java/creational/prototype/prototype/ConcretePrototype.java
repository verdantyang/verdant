package creational.prototype.prototype;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/27
 * Desc:   实例化原型
 */
public class ConcretePrototype extends Prototype {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcretePrototype.class, DesignPatternEnum.Prototype);

    public void valid(ConcretePrototype cp) {
        logger.log(this != cp ? "Cloned" : "False");
    }
}
