package com.verdant.demo.common.bytecode.manipulation.assist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * <p>文件名称：ClassInfoUtils.java</p>
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
public class ClassInfoUtils {
    public static void getClassInfo(String classPath) throws NotFoundException {
        // 获取默认类型池对象
        ClassPool classPool = ClassPool.getDefault();

        // 获取指定的类型
        CtClass ctClass = classPool.get(classPath);

        System.out.println(ctClass.getName());  // 获取类名
        System.out.println("\tpackage " + ctClass.getPackageName());    // 获取包名
        System.out.print("\t" + Modifier.toString(ctClass.getModifiers()) + " class " + ctClass.getSimpleName());   // 获取限定符和简要类名
        System.out.print(" extends " + ctClass.getSuperclass().getName());  // 获取超类
        // 获取接口
        if (ctClass.getInterfaces() != null) {
            System.out.print(" implements ");
            boolean first = true;
            for (CtClass c : ctClass.getInterfaces()) {
                if (first) {
                    first = false;
                } else {
                    System.out.print(", ");
                }
                System.out.print(c.getName());
            }
        }
        System.out.println();
    }
}
