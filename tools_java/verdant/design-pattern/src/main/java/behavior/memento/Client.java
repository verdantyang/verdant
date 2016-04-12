package behavior.memento;

import behavior.memento.caretaker.Caretaker;
import behavior.memento.memento.Memento;
import behavior.memento.originator.Originator;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Func:   备忘录模式
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("state1");
        Memento memento = caretaker.createMemento(originator.getState());
        caretaker.setMemento(memento);
        originator.setState("state2");
        originator.restoreState(caretaker.getMemento());
    }
}
