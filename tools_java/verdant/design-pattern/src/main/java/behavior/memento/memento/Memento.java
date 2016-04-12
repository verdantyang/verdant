package behavior.memento.memento;

import java.util.Map;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   备忘录
 */
public class Memento {
    private String state = "";

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
