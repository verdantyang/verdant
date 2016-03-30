package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化组件A2
 */
public class ConcreteComponentA2 implements AbstractComponent2 {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteComponentA2.class, DesignPatternEnum.AbstractFactory);

    public ConcreteComponentA2(){
        logger.log("Create -> " + DebugLog.getClassName(this.getClass()));
    }
}