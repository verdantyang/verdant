package com.verdant.demo.common.bytecode.manipulation.assist.proxy;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * <p>文件名称：ProxyUtils.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 优行科技 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>创建日期：2017/2/25</p>
 *
 * @author congyu.yang@geely.com
 * @version 1.0
 */
public class ProxyUtils {
    public static void proxy() throws Exception {
        // 实例化代理类工厂
        ProxyFactory factory = new ProxyFactory();

        //设置父类，ProxyFactory将会动态生成一个类，继承该父类
        factory.setSuperclass(ProxyObject.class);

        //设置过滤器，判断哪些方法调用需要被拦截
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method m) {
                return m.getName().startsWith("get");
            }
        });

        Class<?> clazz = factory.createClass();
        ProxyObject proxy = (ProxyObject) clazz.newInstance();
        ((javassist.util.proxy.ProxyObject) proxy).setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                System.out.println(thisMethod.getName() + " invoke start");
                try {
                    Object ret = proceed.invoke(self, args);
                    System.out.println("return: " + ret);
                    return ret;
                } finally {
                    System.out.println(thisMethod.getName() + " invoke end");
                }
            }
        });

        proxy.setName("Test");
        proxy.setValue("1000");
        proxy.getName();
        proxy.getValue();
    }
}
