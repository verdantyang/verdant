package com.verdant.demo.common.bytecode.manipulation.assist.generator;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * <p>文件名称：DisplayGenerator.java</p>
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
public class HelloGenerator {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc= pool.makeClass("assist.Display");

        CtMethod method = CtNewMethod.make("public void code(){}", cc);
        method.insertBefore("System.out.println(\"Hello World!\");");
        cc.addMethod(method);

        cc.writeFile("d://temp");
    }
}