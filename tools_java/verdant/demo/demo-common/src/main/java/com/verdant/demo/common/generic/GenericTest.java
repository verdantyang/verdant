package com.verdant.demo.common.generic;

import com.verdant.jtools.common.utils.ReflectUtils2;

import java.util.List;

/**
 * 泛型擦除
 *
 * @author verdant
 * @since 2016/07/04
 */
public class GenericTest {
    public void compileError(List<String> ls) {
        System.out.println("Sting");
    }

    //compileError
//    public void compileError(List<Integer> li){
//        System.out.println("Integer");
//    }

    public static void genericErase() {
        BaseImpl base = new BaseImpl();
        base.flag = 2;
        BaseImpl2 base2 = new BaseImpl2();
        base2.flag = 3;
        Class<?> clazz = ReflectUtils2.getSuperClassGenricType(base.getClass(), 0);
        Class<?> clazz2 = ReflectUtils2.getSuperClassGenricType(base2.getClass(), 0);
        System.out.println(clazz.getName());
        System.out.println(clazz2.getName());
        System.out.println(base.flag);
    }
    public static void main(String[] args) {
        genericErase();
    }
}
