package com.verdant.demo.common.enhance.asm.loader;

import org.objectweb.asm.Opcodes;

/**
 * 自定义ClassLoader
 *
 * @author verdant
 * @since 2017/02/09
 */
public class MyClassLoader extends ClassLoader implements Opcodes {

    public MyClassLoader() {
        super();
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public Class<?> defineClass(String name, byte[] b) {
        return super.defineClass(name, b, 0, b.length);
    }

}
