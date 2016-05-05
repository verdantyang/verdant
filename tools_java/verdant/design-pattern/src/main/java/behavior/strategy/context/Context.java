package behavior.strategy.context;

import behavior.strategy.strategy.Strategy;

/**
 * Author: verdant
 * Create: 2016/3/23
 * Desc:   执行环境
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }
    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }
    public void execute(){
        this.strategy.execute();
    }
}
