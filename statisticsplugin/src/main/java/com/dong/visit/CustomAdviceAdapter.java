package com.dong.visit;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * 方法访问者
 * onMethodEnter 、 onMethodExit中代码的生成方式：
 * 首先我们先写好java文件，然后执行javac 生成.class文件，然后执行
 * java -classpath "asm-all-5.2.jar" org.objectweb.asm.util.ASMifier TestAsm.class
 * 就可以生成asm code，然后把需要的代码拷贝过来即可。
 * <p>
 * Created by dong on 2017/9/14.
 */
public class CustomAdviceAdapter extends AdviceAdapter {

    private boolean isFlag = false;
    private String methodName;

    protected CustomAdviceAdapter(String methodName, MethodVisitor methodVisitor, int access, String name, String desc) {
        super(Opcodes.ASM5, methodVisitor, access, name, desc);
        this.methodName = methodName;
    }

    private boolean isFlag() {
        return isFlag;
    }

    @Override
    public void visitCode() {
        super.visitCode();
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, desc, signature, start, end, index);
        if (isFlag()) {
            System.out.println("===visitLocalVariable: " + name + "," + desc + ",methodName:" + methodName);
        }
    }

    /**
     * 当我们在某个方法写上注解的时候，它会执行这个方法，
     * 我们可以在这个方法判断是否是我们注解做一些事情，比如重置一个标志位；
     */
    @Override
    public org.objectweb.asm.AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(SaFlag.class).equals(desc)) {
            isFlag = true;
        }

        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitParameter(String s, int i) {
        System.out.println("===visitParameter--" + s + "---" + i);
        super.visitParameter(s, i);
    }

    @Override
    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String s, boolean b) {
        System.out.println("===visitInsnAnnotation--" + i + "--" + typePath.toString() + "---" + s + "---b");
        return super.visitInsnAnnotation(i, typePath, s, b);
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int i, String s, boolean b) {
        System.out.println("===visitParameterAnnotation--" + i + "---" + s + "---" + b);
        return super.visitParameterAnnotation(i, s, b);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.out.println("===visitAttribute---" + attribute.type);
        super.visitAttribute(attribute);
    }

    /**
     * 方法进入调用
     */
    @Override
    protected void onMethodEnter() {
        if (isFlag()) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("========start=========" + methodName);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V", false);

            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "com/dong/code/TimeCache", "setStartTime",
                    "(Ljava/lang/String;J)V", false);

        }
    }

    /**
     * 方法结束时调用
     */
    @Override
    protected void onMethodExit(int i) {
        if (isFlag()) {
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "com/dong/code/TimeCache", "setEndTime",
                    "(Ljava/lang/String;J)V", false);

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "com/dong/code/TimeCache", "getExecuteTime",
                    "(Ljava/lang/String;)Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V", false);

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("========end=========");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V", false);
        }
    }
}
