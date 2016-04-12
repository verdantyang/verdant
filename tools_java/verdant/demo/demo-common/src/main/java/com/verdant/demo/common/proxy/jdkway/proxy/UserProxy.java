package com.verdant.demo.common.proxy.jdkway.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   动态代理
 */
public class UserProxy implements InvocationHandler {

    //被代理对象
    private Object target;

    //织入的代码
    private void beforMethod(Method method) {
        System.out.println(method.getName() + " begin " + new Date());
    }
    private void afterMethod(Method method) {
        System.out.println(method.getName() + " end " + new Date());
    }

    public Object bind(Object target) {
        this.target = target;

        //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        beforMethod(method);
        result = method.invoke(target, args);
        afterMethod(method);
        return result;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}

