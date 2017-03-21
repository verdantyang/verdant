package com.verdant.demo.common.bytecode.manipulation.assist;

import com.verdant.demo.common.bytecode.base.loader.MyClassLoader2;
import javassist.*;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件名称：AssistTest.java</p>
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
public class AssistTest {
    private static ClassLoader getLocaleClassLoader() throws Exception {
        File libPath = new File("d://temp//assist");
        File clazzPath = new File("d://temp//assist//Display.class");
        List<URL> classPathURLs = new ArrayList<>();
        // 加载.class文件路径
        classPathURLs.add(clazzPath.toURI().toURL());
        // 获取所有的jar文件
        File[] jarFiles = libPath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
//        Assert.assertFalse(0 == jarFiles.length);

        // 将jar文件路径写入集合
//        for (File jarFile : jarFiles) {
//            classPathURLs.add(jarFile.toURI().toURL());
//        }

        // 实例化类加载器
        return new URLClassLoader(classPathURLs.toArray(new URL[classPathURLs.size()]));
    }

    //
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
    public static void main(String[] args) {
        File file = new File("d:\\temp\\");

        try {
//             Convert File to a URL
//            URL url = file.toURL();          // file:/c:/myclasses/
//            URL[] urls = new URL[]{url};
//
//            ClassLoader cl = new URLClassLoader(urls);
//
//            Class cls = cl.loadClass("assist.Display");
//            System.out.println(cls.getName());

            // 获取本地类加载器
        URL classes = new URL("file:/d:/temp/");
        URL[] urls = new URL[]{classes};
        MyClassLoader2 classLoader = new MyClassLoader2(urls, null);
        classLoader.addJar(new File("d://temp//").toURI().toURL());

//            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//            ClassLoaderUtils.loadClass("d:\\temp\\");
            // 获取要修改的类
            Class<?> clazz = classLoader.loadClass("assist.Display");
            // 实例化类型池对象
            ClassPool classPool = ClassPool.getDefault();
            // 设置类搜索路径
            classPool.appendClassPath(new ClassClassPath(clazz));
            // 从类型池中读取指定类型
            CtClass ctClass = classPool.get(clazz.getName());

            // 获取String类型参数集合
            CtClass[] paramTypes = {classPool.get(String.class.getName())};
            // 获取指定方法名称
            CtMethod method = ctClass.getDeclaredMethod("code");
            // 赋值方法到新方法中
            CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);
            // 修改源方法名称
            String oldName = method.getName() + "$Impl";
            method.setName(oldName);

            // 修改原方法
            newMethod.setBody("{System.out.println(\"执行前\");" + oldName + "($$);System.out.println(\"执行后\");}");
            // 将新方法添加到类中
            ctClass.addMethod(newMethod);

            // 加载重新编译的类
            clazz = ctClass.toClass();      // 注意，这一行会将类冻结，无法在对字节码进行编辑
            // 执行方法
            clazz.getMethod("show", String.class).invoke(clazz.newInstance(), "hello");
            ctClass.defrost();  // 解冻一个类，对应freeze方法
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
