package creational.prototype.prototype;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ConcretePrototype extends Prototype {
    public void valid(ConcretePrototype cp) {
        DebugLog.print(this != cp ? "Cloned" : "False",
                DesigiPatternEnum.Prototype, ConcretePrototype.class);
    }
}
