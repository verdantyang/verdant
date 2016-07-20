package structural.decorator.case1.decorater;

import structural.decorator.case1.component.Animal;
import structural.decorator.case1.component.Feature;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * 代理装饰动物
 *
 * @author verdant
 * @since 2016/07/20
 */
public class DecorateAnimal implements Animal {
    //被包装的动物
    private Animal animal;
    //包装器
    private Class<? extends Feature> clz;

    public DecorateAnimal(Animal animal, Class<? extends Feature> clz) {
        this.animal = animal;
        this.clz = clz;
    }

    @Override
    public void doStuff() {
        InvocationHandler handler = new InvocationHandler() {
            //具体包装行为
            @Override
            public Object invoke(Object p, Method m, Object[] args) throws Throwable {
                Object obj = null;
                //包装条件
                if (Modifier.isPublic(m.getModifiers())) {
                    obj = m.invoke(clz.newInstance(), args);
                }
                animal.doStuff();
                return obj;
            }
        };

        ClassLoader cl = clz.getClass().getClassLoader();
        //动态代理，由Handler决定如何包装
        Feature proxy = (Feature) Proxy.newProxyInstance(cl, clz.getInterfaces(), handler);
        proxy.load();
    }
}