package behavior.iterator;

import behavior.iterator.aggregate.Aggregate;
import behavior.iterator.aggregate.ConcreteAggregate;
import behavior.iterator.iterator.Iterator;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Func:   迭代器模式
 */
public class Client {
    private static final DebugLog logger = DebugLogFactory.getLogger(Client.class, DesignPatternEnum.Iterator);

    public static void main(String[] args) {
        Object[] objArray = {"One", "Two", "Three", "Four", "Five", "Six"};
        Aggregate agg = new ConcreteAggregate(objArray);

        Iterator it = agg.createIterator();
        while (it.hasNext()) {
            logger.log("Get " + it.current());
            it.next();
        }
    }
}
