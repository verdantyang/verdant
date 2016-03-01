package creational.factory.product;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化产品B
 */
public class ConcreteProductB implements AbstractProduct{
    public ConcreteProductB() {
        DebugLog.print("Create -> ProductB", DesigiPatternEnum.Factory, ConcreteProductA.class);
    }
}
