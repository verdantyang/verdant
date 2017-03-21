package com.verdant.demo.common.bytecode.manipulation.asm.visitor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 方法增强
 *
 * @author verdant
 * @since 2017/02/09
 */
public class AopMethodVisitor extends MethodVisitor implements Opcodes {

    public AopMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    private String prefix = "com/verdant/demo/common/bytecode/manipulation/asm/interceptor/";

    @Override
    public void visitCode() {
        super.visitCode();
        this.visitMethodInsn(INVOKESTATIC, prefix + "AopInterceptor", "before", "()V", false);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode >= IRETURN && opcode <= RETURN)   // 在返回之前插入after代码
            this.visitMethodInsn(INVOKESTATIC, prefix + "AopInterceptor", "after", "()V", false);
        super.visitInsn(opcode);
    }

}
