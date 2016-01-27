package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Created by verdant on 2016/1/14.
 * Desc: 实例化组件AB
 */
public class ConcreteComponentAB implements AbstractComponentA {
    public ConcreteComponentAB(){
        DebugLog.print("Create -> ConcreteComponentAB", DesigiPatternEnum.AbstractFactory, ConcreteComponentAA.class);
    }
}