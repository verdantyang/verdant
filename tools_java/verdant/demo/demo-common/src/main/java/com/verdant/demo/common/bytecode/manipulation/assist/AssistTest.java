package com.verdant.demo.common.bytecode.manipulation.assist;

import com.verdant.demo.common.bytecode.base.loader.ClassLoaderUtils;
import javassist.*;

import java.net.URLClassLoader;

/**
 * <p>文件名称：AssistTest.java</p>
 * <p>文件描述：</p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>创建日期：2017/2/25</p>
 *
 * @author congyu.yang@geely.com
 * @version 1.0
 */
public class AssistTest {
//    @Test
//    public void classInfoTest() throws NotFoundException {
//        ClassInfoUtils.getClassInfo("java.lang.String");
//    }
//
//    @Test
//    public void proxyTest() throws NotFoundException {
//        try {
//            ProxyUtils.proxy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//

    public static void main(String[] args) {

        try {
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            ClassLoaderUtils.loadClass("d:\\temp\\");
            // 获取要修改的类
            Class<?> clazz = classLoader.loadClass("assist.Display");
            // 实例化类型池对象
            ClassPool classPool = ClassPool.getDefault();
            // 设置类搜索路径
            classPool.appendClassPath(new ClassClassPath(clazz));
            // 从类型池中读取指定类型
            CtClass ctClass = classPool.get(clazz.getName());

            // 获取指定方法名称
            CtMethod method = ctClass.getDeclaredMethod("code");
            // 赋值方法到新方法中
            CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);
            // 修改源方法名称
            String newName = method.getName() + "$Impl";
            newMethod.setName(newName);

            ctClass.setName(clazz.getName() + "$Impl");
            // 修改原方法
            newMethod.setBody("{System.out.println(\"执行前\");" + method.getName() + "($$);System.out.println(\"执行后\");}");
            // 将新方法添加到类中
            ctClass.addMethod(newMethod);

            // 加载重新编译的类
            clazz = ctClass.toClass();      // 注意，这一行会将类冻结，无法再对字节码进行编辑
            // 执行方法
            clazz.getMethod(newName).invoke(clazz.newInstance());
            ctClass.defrost();  // 解冻一个类，对应freeze方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
