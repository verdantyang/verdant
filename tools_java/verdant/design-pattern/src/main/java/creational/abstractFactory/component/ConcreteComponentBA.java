package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化组件BA
 */
public class ConcreteComponentBA implements AbstractComponentB {
    public ConcreteComponentBA(){
        DebugLog.print("Create -> ConcreteComponentBA", DesigiPatternEnum.AbstractFactory, ConcreteComponentAA.class);
    }
}