package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Created by verdant on 2016/1/14.
 * Desc: 实例化组件AA
 */
public class ConcreteComponentAA implements AbstractComponentA {
    public ConcreteComponentAA(){
        DebugLog.print("Create -> ConcreteComponentAA", DesigiPatternEnum.AbstractFactory, ConcreteComponentAA.class);
    }
}