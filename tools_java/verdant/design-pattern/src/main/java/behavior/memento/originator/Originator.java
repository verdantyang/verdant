package behavior.memento.originator;

import behavior.memento.memento.Memento;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   发起人
 */
public class Originator {
    private static final DebugLog logger = DebugLogFactory.getLogger(Originator.class, DesignPatternEnum.Memento);
    private String state = "";

    public String getState() {
        return state;
    }
    public void setState(String state) {
        logger.log("Set state: " + state);
        this.state = state;
    }
    public void restoreState(Memento memento){
        logger.log("Restore state from memento: " + memento.getState());
        this.setState(memento.getState());
    }
}
