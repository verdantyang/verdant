package structural.flyweight.factory;

import structural.flyweight.flyweight.ConcreteFlyweight;
import structural.flyweight.flyweight.Flyweight;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

import java.util.Hashtable;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Desc:   享元工厂
 */
public class FlyweightFactory {

    private static final DebugLog logger = DebugLogFactory.getLogger(FlyweightFactory.class, DesignPatternEnum.Flyweight);

    /**
     * 存储享元的哈希表
     */
    private Hashtable<String, Flyweight> flyweights = new Hashtable();

    public FlyweightFactory() {
    }

    /**
     * 获取享元角色，如果是空则新建一个
     * @param type
     * @return
     */
    public Flyweight getFlyWeight(String type) {
        Flyweight flyweight = flyweights.get(type);
        if (null == flyweight) {
            flyweight = new ConcreteFlyweight(type);
            flyweights.put(type, flyweight);
        }
        return flyweight;
    }

    public int getFlyweightSize() {
        logger.log("Size: " + flyweights.size());
        return flyweights.size();
    }
}
