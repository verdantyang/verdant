package structural.adapter;

import structural.adapter.target.ConcreteTarget;
import structural.adapter.target.Target;
import structural.adapter.adapter.Adapter;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Func:   适配器模式
 */
public class Client {
    public static void main(String[] args) {
        // 使用普通功能类
        Target concreteTarget = new ConcreteTarget();
        concreteTarget.request();

        // 使用适配类
        Target adapter = new Adapter();
        adapter.request();
    }
}
