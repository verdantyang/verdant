package com.verdant.demo.common.enhance.asm.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 类增强
 *
 * @author verdant
 * @since 2017/02/09
 */
public class AopClassVisitor extends ClassVisitor {

    public AopClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        // 对test开头的方法进行特殊处理
        if (name.startsWith("test")) {
            mv = new AopMethodVisitor(this.api, mv);
        }
        return mv;
    }
}
