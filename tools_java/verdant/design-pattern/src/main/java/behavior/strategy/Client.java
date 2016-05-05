package behavior.strategy;

import behavior.strategy.context.Context;
import behavior.strategy.strategy.ConcreteStrategy1;
import behavior.strategy.strategy.ConcreteStrategy2;
import behavior.strategy.strategy.ConcreteStrategy3;

/**
 * Author: verdant
 * Create: 2016/3/23
 * Func:   策略模式
 */
public class Client {
    public static void main(String[] args) {
        Context context;

        context = new Context(new ConcreteStrategy1());
        context.execute();

        context.setStrategy(new ConcreteStrategy2());
        context.execute();

        context.setStrategy(new ConcreteStrategy3());
        context.execute();
    }
}
