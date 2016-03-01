package creational.prototype.prototype;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/27
 * Desc:   实例化原型
 */
public class ConcretePrototype extends Prototype {
    public void valid(ConcretePrototype cp) {
        DebugLog.print(this != cp ? "Cloned" : "False",
                DesigiPatternEnum.Prototype, ConcretePrototype.class);
    }
}
