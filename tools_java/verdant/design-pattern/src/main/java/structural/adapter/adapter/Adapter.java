package structural.adapter.adapter;

import structural.adapter.target.Target;
import structural.adapter.adaptee.Adaptee;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   适配器
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.specificRequest();
    }
}
