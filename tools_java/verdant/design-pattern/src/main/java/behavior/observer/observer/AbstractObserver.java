package behavior.observer.observer;

import behavior.observer.subject.Subject;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

import java.util.Observable;
import java.util.Observer;

/**
 * Author: verdant
 * Create: 2016/3/29
 * Desc:   抽象观察者
 */
public abstract class AbstractObserver implements Observer {

    private static final DebugLog logger = DebugLogFactory.getLogger(AbstractObserver.class, DesignPatternEnum.Observer);
    private static final String format = "Notice %s data changed -> %s";

    public AbstractObserver(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        logger.log(String.format(format, DebugLog.getClassName(this.getClass()), ((Subject) o).getData()));
    }
}
