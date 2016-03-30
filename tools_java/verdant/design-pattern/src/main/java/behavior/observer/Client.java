package behavior.observer;

import behavior.observer.observer.ConcreteObserver1;
import behavior.observer.observer.ConcreteObserver2;
import behavior.observer.subject.Subject;

import java.util.Observer;

/**
 * Author: verdant
 * Create: 2016/3/29
 * Func:   观察者模式
 */
public class Client {
    public static void main(String[] args) {

        Subject subject = new Subject();
        Observer observer1 = new ConcreteObserver1(subject);
        Observer observer2 = new ConcreteObserver2(subject);
        subject.setData("start");
        subject.setData("run");
        subject.setData("stop");
    }
}
