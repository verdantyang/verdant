package structural.flyweight.flyweight;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Desc:   实例化享元角色
 */
public class ConcreteFlyweight implements Flyweight {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteFlyweight.class, DesignPatternEnum.Flyweight);

    private String name;

    public ConcreteFlyweight(String str) {
        this.name = str;
    }

    public void operation() {
        logger.log("Operate " + name);
    }

}
