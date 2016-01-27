package creational.factory.product;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Created by verdant on 2016/1/14.
 */
public class ConcreteProductB implements AbstractProduct{
    public ConcreteProductB() {
        DebugLog.print("Create -> ConcreteProductB", DesigiPatternEnum.Factory, ConcreteProductA.class);
    }
}
