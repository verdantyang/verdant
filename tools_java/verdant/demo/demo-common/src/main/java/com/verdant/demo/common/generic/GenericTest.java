package com.verdant.demo.common.generic;

import com.verdant.jtools.common.utils.base.ReflectUtils2;

import java.util.List;

/**
 * 泛型测试
 *
 * @author verdant
 * @since 2016/07/04
 */
public class GenericTest {
    private static BaseImplA base1 = new BaseImplA();
    private static BaseImplB base2 = new BaseImplB();

    //compileError
    public void compileError(List<String> ls) {
        System.out.println("Sting");
    }

//    public void compileError(List<Integer> li){
//        System.out.println("Integer");
//    }

    public static void genericErase() {
        base1.flag = 2;
        base2.flag = 3;
        System.out.println(base1.flag);
    }

    public static void genericReflect() {
        Class<?> clazz = ReflectUtils2.getClassGenericType(base1.getClass());
        Class<?> clazz2 = ReflectUtils2.getClassGenericType(base2.getClass());
        System.out.println(clazz.getName());
        System.out.println(clazz2.getName());
    }


    public static void main(String[] args) {
        genericReflect();
    }
}
