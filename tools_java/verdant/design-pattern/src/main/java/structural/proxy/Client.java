package structural.proxy;

import structural.proxy.proxy.Proxy;
import structural.proxy.subject.RealSubject;
import structural.proxy.subject.Subject;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Func:   代理模式
 */
public class Client {
    public static void main(String[] args) {
        Subject subject = new RealSubject();
        Proxy proxy = new Proxy(subject);
        proxy.operate();
    }
}
