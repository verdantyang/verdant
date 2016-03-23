package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化组件B1
 */
public class ConcreteComponentB1 implements AbstractComponent1 {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteComponentA1.class, DesignPatternEnum.AbstractFactory);

    public ConcreteComponentB1(){
        logger.log("Create -> ConcreteComponentB1");
    }
}