package asm.modify;

import org.objectweb.asm.*;

/**
 * User: xiaofei.wxf
 * Date: 13-11-6 上午9:30
 */
public class AopClassAdaptor extends ClassAdapter implements Opcodes {
    /**
     * Constructs a new {@link org.objectweb.asm.ClassAdapter} object.
     *
     * @param cv the class visitor to which this adapter must delegate calls.
     */
    public AopClassAdaptor(ClassVisitor cv) {
        super(cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
        String[] interfaces) {
        cv.visit(version, access, name, signature, superName,
            interfaces);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
        String[] exceptions) {
        MethodVisitor ret = null;
        ret = cv.visitMethod(access, name, desc, signature, exceptions);
        if (null != ret && name.equals("exec")) {
            System.out.println("visitMethod");
            return new AopMethodAdapter(ret);
        }
        return ret;
    }

    @Override
    public void visitEnd() {
        cv.visitEnd();
    }

    class AopMethodAdapter extends MethodAdapter implements Opcodes {
        public AopMethodAdapter(MethodVisitor mv) {
            super(mv);
        }

        @Override
        public void visitCode() {
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Before Method");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V");
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("After Method");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V");
            }
            mv.visitInsn(opcode);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            mv.visitMaxs(maxStack + 1, maxLocals);
        }
    }
}
