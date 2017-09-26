package com.dong.visit;

import com.dong.visit.log.LogUtils;

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

    private String TAG = "ClassVisitorAdapter";
    private FieldEntity fieldEntity;
    private String outerClassFullName;

    public ClassVisitorAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
        fieldEntity = new FieldEntity();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        LogUtils.println(TAG, "--visit--" + name);
//        classFullName = name.replaceAll("/", ".");
        fieldEntity.setClassFullName(name);
    }

    @Override
    public void visitSource(String s, String s1) {
        super.visitSource(s, s1);
        LogUtils.println(TAG, "--visitSource--" + s + "---" + s1);
    }

    @Override
    public void visitInnerClass(String classFullName, String s1, String s2, int i) {
        super.visitInnerClass(classFullName, s1, s2, i);
        LogUtils.println(TAG, "--visitInnerClass--" + classFullName + "---" + s1 + "---" + s2);
    }

    @Override
    public void visitOuterClass(String outerClassFullName, String methodName, String methodDesc) {
        super.visitOuterClass(outerClassFullName, methodName, methodDesc);
        LogUtils.println(TAG, "--visitOuterClass--" + outerClassFullName + "--" + methodName + "--" + methodDesc);
        this.outerClassFullName = outerClassFullName;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        LogUtils.println(TAG, "--visitAnnotation--" + desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public FieldVisitor visitField(int i, String fieldName, String desc, String s2, Object o) {
        LogUtils.println(TAG, "--visitField--" + fieldName + "---" + desc + "--" + s2 + "--" + o);
        if (fieldName.startsWith("this$") && desc.contains(outerClassFullName)) {
            fieldEntity.setRefVarName(fieldName);
            fieldEntity.setRefVarType(desc);
            fieldEntity.setRefClassFullName(outerClassFullName);
        }
        FieldVisitor fieldVisitor = super.visitField(i, fieldName, desc, s2, o);
        if (fieldVisitor != null) {
            fieldVisitor = new FieldVisitorAdapter(fieldName, fieldEntity, fieldVisitor);
        }

        return fieldVisitor;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        LogUtils.println(TAG, "--visitMethod--" + name + "---" + desc);
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        //自定义方法访问者
        methodVisitor = new MethodVisitorAdapter(fieldEntity, methodVisitor, access, name, desc);
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        LogUtils.println(TAG, "--visitEnd--");
    }
}
