package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化组件B2
 */
public class ConcreteComponentB2 implements AbstractComponent2 {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteComponentB2.class, DesignPatternEnum.AbstractFactory);

    public ConcreteComponentB2(){
        logger.log("Create -> ConcreteComponentB2");
    }
}