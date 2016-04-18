package structural.facade.facade;

import structural.facade.module.Module1;
import structural.facade.module.Module2;
import structural.facade.module.Module3;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Desc:   门面
 */
public class Facade {
    public void handle(){
        Module1 a = new Module1();
        a.execute();
        Module2 b = new Module2();
        b.execute();
        Module3 c = new Module3();
        c.execute();
    }
}
