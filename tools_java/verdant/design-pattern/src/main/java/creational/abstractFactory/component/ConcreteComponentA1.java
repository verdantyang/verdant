package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化组件A1
 */
public class ConcreteComponentA1 implements AbstractComponent1 {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteComponentA1.class, DesignPatternEnum.AbstractFactory);

    public ConcreteComponentA1(){
        logger.log("Create -> " + DebugLog.getClassName(this.getClass()));
    }
}