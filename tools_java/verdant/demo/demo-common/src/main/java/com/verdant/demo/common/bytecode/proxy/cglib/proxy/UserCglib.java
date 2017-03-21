package com.verdant.demo.common.bytecode.proxy.cglib.proxy;

import java.lang.reflect.Method;
import java.util.Date;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   Cglib动态代理
 */
public class UserCglib implements MethodInterceptor {

    private Object target;

    //织入的代码
    private void beforMethod(Method method) {
        System.out.println(method.getName() + " begin " + new Date());
    }
    private void afterMethod(Method method) {
        System.out.println(method.getName() + " end " + new Date());
    }

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    /**
     * 回调方法
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        beforMethod(method);
        proxy.invokeSuper(obj, args);
        afterMethod(method);
        return null;
    }
}
