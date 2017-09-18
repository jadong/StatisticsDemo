package com.dong.visit;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 类成员访问
 * Created by dong on 2017/9/7.
 */
public class SaFlagMethodClassVisitor extends ClassVisitor {

    private String targetPackageName;
    private String targetClassName;

    public SaFlagMethodClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println("####--visit---" + name);
    }

    @Override
    public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
        System.out.println("####--visitField---" );
        return super.visitField(i, s, s1, s2, o);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        System.out.println("####--visitAnnotation---" );
        return super.visitAnnotation(s, b);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        //自定义方法访问者
        methodVisitor = new CustomAdviceAdapter(name, methodVisitor, access, name, desc);
        return methodVisitor;
    }

}
