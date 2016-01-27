package creational.abstractFactory.component;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Created by verdant on 2016/1/14.
 * Desc: 实例化组件BB
 */
public class ConcreteComponentBB implements AbstractComponentB {
    public ConcreteComponentBB(){
        DebugLog.print("Create -> ConcreteComponentBB", DesigiPatternEnum.AbstractFactory, ConcreteComponentAA.class);
    }
}