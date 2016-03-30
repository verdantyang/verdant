package behavior.observer.subject;


import org.apache.commons.lang3.StringUtils;

import java.util.Observable;

/**
 * Author: verdant
 * Create: 2016/3/29
 * Desc:   被观察者
 */
public class Subject extends Observable {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String str) {
        if (!StringUtils.equals(this.data, str)) {
            this.data = str;
            setChanged();
            notifyObservers();
        }
    }
}
