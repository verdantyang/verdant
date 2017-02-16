package com.verdant.demo.common.enhance.asm;

import com.verdant.demo.common.enhance.asm.loader.MyClassLoader;
import com.verdant.demo.common.enhance.asm.visitor.AopClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * 通过ASM生成类的字节码
 *
 * @author Administrator
 */
public class AsmTest implements Opcodes {

    private static String prefix = "com/verdant/demo/common/enhance/asm/ori/";


    public static void main(String[] args) {
        try {
//            byte[] data = generatorHelloClass(prefix);
//            ClassReader cr = new ClassReader(data);

            ClassWriter cw = new ClassWriter(0);
            ClassReader cr = new ClassReader("com.verdant.demo.common.enhance.asm.ori.Hello");
            cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC,
                    prefix + "Hello", null,
                    "java/lang/Object", null);
            cr.accept(new AopClassVisitor(ASM5, cw), ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();

            MyClassLoader myClassLoader = new MyClassLoader();
            Class<?> helloClass = myClassLoader.defineClass("com.verdant.demo.common.enhance.asm.ori.Hello", data);
            Object obj = helloClass.newInstance();
            Method method = helloClass.getMethod("display");
            method.invoke(obj);

            method = helloClass.getMethod("testList");
            Object result = method.invoke(obj);
            System.out.println(result);

            // 将这个类输出到原先的类文件目录下，这是原先的类文件已经被修改
            File file = new File("D:\\temp\\Hello.class");
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(data);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
