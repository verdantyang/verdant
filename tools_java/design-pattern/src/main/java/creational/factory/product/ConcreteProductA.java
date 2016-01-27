package creational.factory.product;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Created by verdant on 2016/1/14.
 */
public class ConcreteProductA implements AbstractProduct {
    public ConcreteProductA() {
        DebugLog.print("Create -> ConcreteProductA", DesigiPatternEnum.Factory, ConcreteProductA.class);
    }
}
