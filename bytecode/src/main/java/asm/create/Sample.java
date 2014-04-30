package asm.create;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * User: xiaofei.wxf
 * Date: 13-11-5 上午11:51
 */
public class Sample<T extends ProxyInterface> extends ClassLoader {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        ClassWriter cw = new ClassWriter(1);
        cw.visit(V1_5, ACC_PUBLIC,
                "asm/Proxy1", null, "java/lang/Object",
            new String[] {"asm/ProxyInterface"});
        //构造函数
        MethodVisitor init = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        init.visitCode();
        Label l_1 = new Label();
        init.visitLabel(l_1);
        init.visitLineNumber(4, l_1);
        init.visitVarInsn(ALOAD, 0);
        init.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        init.visitInsn(RETURN);
        Label l00 = new Label();
        init.visitLabel(l00);
        init.visitLocalVariable("this", "Lxiaofei/asm/Sample;", null, l_1, l00, 0);
        init.visitMaxs(1, 1);
        init.visitEnd();
        //handle方法


        MethodVisitor mv =
            cw.visitMethod(ACC_PUBLIC, "handle", "(Ljava/lang/String;)V", null, null);
        mv.visitCode();

        Label returnLabel = new Label();

        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(7, l0);

        mv.visitVarInsn(ALOAD, 1);
        Label l1 = new Label();
        mv.visitJumpInsn(IFNULL, l1);
        mv.visitLdcInsn("");
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z");
        Label l2 = new Label();
        mv.visitJumpInsn(IFEQ, l2);
        mv.visitLabel(l1);

        mv.visitLineNumber(8, l1);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitInsn(RETURN);
        mv.visitLabel(l2);
        mv.visitLineNumber(9, l2);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        List<String> modules = new ArrayList<String>();
        modules.add("Login");
        modules.add("Friend");
        int currentLine = 9;
        for (String each : modules) {
            addMethod(mv, returnLabel, each, currentLine);
            currentLine += 2;
        }

        mv.visitLabel(returnLabel);
        mv.visitLineNumber(18, returnLabel);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitInsn(RETURN);
        Label l9 = new Label();
        mv.visitLabel(l9);
        mv.visitLocalVariable("this", "Lxiaofei/asm/Sample;", null, l0, l9, 0);
        mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l9, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        byte[] classBytecode = cw.toByteArray();
        Class<? extends ProxyInterface> c = (Class<? extends ProxyInterface>) new Sample()
            .defineClass("asm.Proxy1", classBytecode, 0, classBytecode.length);
        ProxyInterface proxyInterface = c.newInstance();
        proxyInterface.handle("Login");
        proxyInterface.handle("Friend");
    }

    private static final void addMethod(final MethodVisitor mv, final Label returnLabel,
        final String prefix, int currentLine) {
        final String whileClassName =
            new StringBuilder("asm/").append(prefix).append("Handler").toString();
        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn(prefix);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z");
        Label l3 = new Label();
        mv.visitJumpInsn(IFEQ, l3);
        Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitLineNumber(++currentLine, l4);
        mv.visitTypeInsn(NEW, whileClassName);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, whileClassName, "<init>", "()V");
        mv.visitMethodInsn(INVOKEVIRTUAL, whileClassName, "handle", "()V");
        mv.visitJumpInsn(GOTO, returnLabel);
        mv.visitLabel(l3);
        mv.visitLineNumber(++currentLine, l3);
    }
}
