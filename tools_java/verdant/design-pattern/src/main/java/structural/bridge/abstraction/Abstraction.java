package structural.bridge.abstraction;

import structural.bridge.implementor.Implementor;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   抽象角色
 */
public abstract class Abstraction {
    protected Implementor impl;

    public Abstraction(Implementor impl){
        this.impl = impl;
    }
    //示例方法
    public void operation(){
        impl.operationImpl();
    }
}
