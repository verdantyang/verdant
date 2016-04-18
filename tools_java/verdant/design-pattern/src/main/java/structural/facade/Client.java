package structural.facade;

import structural.facade.facade.Facade;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Func:   门面模式
 */
public class Client {
    public static void main(String[] args) {

        Facade facade = new Facade();
        facade.handle();
    }
}
