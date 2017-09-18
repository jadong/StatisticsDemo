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
public class ClassVisitorAdapter extends ClassVisitor {

    private String targetClassFullName;
    private ParamsEntity paramsEntity = new ParamsEntity();

    public ClassVisitorAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println("ClassVisitor--visit---" + name);
        targetClassFullName = name.replaceAll("/", ".");
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println("ClassVisitor--visitAnnotation--" + desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public FieldVisitor visitField(int i, String fieldName, String s1, String s2, Object o) {
        System.out.println("ClassVisitor--visitField--" + fieldName + "---" + s1 + "--" + s2 + "--" + o);
        FieldVisitor fieldVisitor = super.visitField(i, fieldName, s1, s2, o);
        if (fieldVisitor != null) {
            fieldVisitor = new FieldVisitorAdapter(fieldName,paramsEntity,fieldVisitor);
        }
        return fieldVisitor;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        //自定义方法访问者
        methodVisitor = new MethodVisitorAdapter(name, methodVisitor, access, name, desc);
        return methodVisitor;
    }

}
