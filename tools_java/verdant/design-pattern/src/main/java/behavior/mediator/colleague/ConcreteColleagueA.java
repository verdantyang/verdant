package behavior.mediator.colleague;

import behavior.mediator.mediator.Mediator;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Desc:   实例化实体A
 */
public class ConcreteColleagueA extends Colleague {

    public void setNumber(int number, Mediator med) {
        this.number = number;
        med.AaffectB();
    }
}
