package behavior.memento.caretaker;

import behavior.memento.memento.Memento;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

import java.util.List;
import java.util.Stack;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   管理者
 */
public class Caretaker {
    private static final DebugLog logger = DebugLogFactory.getLogger(Caretaker.class, DesignPatternEnum.Memento);
    private Stack<Memento> mementos = new Stack<>();

    public Memento createMemento(String state){
        return new Memento(state);
    }

    public Memento getMemento() {
        return mementos.pop();
    }

    public void setMemento(Memento memento) {
        logger.log("Back up state: " + memento.getState());
        this.mementos.push(memento);
    }
}
