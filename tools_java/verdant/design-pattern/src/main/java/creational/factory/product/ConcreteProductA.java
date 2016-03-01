package creational.factory.product;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   实例化产品A
 */
public class ConcreteProductA implements AbstractProduct {
    public ConcreteProductA() {
        DebugLog.print("Create -> ProductA", DesigiPatternEnum.Factory, ConcreteProductA.class);
    }
}
