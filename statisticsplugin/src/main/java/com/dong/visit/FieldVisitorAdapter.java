package com.dong.visit;

import com.dong.visit.log.LogUtils;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 字段访问适配
 * Created by dong on 2017/9/18.
 */
public class FieldVisitorAdapter extends FieldVisitor {

    private String TAG = "FieldVisitorAdapter";
    private String filedName;
    private ClassDescEntity classDescEntity;
    private ClassDescEntity.DataField dataField;

    public FieldVisitorAdapter(String filedName, ClassDescEntity classDescEntity, FieldVisitor fieldVisitor) {
        super(Opcodes.ASM5, fieldVisitor);
        this.filedName = filedName;
        this.classDescEntity = classDescEntity;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean b) {
        LogUtils.println(TAG, "--visitAnnotation--" + desc);
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, b);
//        if (Type.getDescriptor(PointArg.class).equals(desc)) {
//            dataField = new ClassDescEntity.DataField();
//            dataField.setDataName(filedName);
//            dataField.setDataType(desc);
//            if (annotationVisitor != null) {
//                annotationVisitor = new AnnotationVisitorAdapter(dataField, annotationVisitor);
//            }
//            classDescEntity.addDataField(dataField);
//
//            LogUtils.println(TAG, "====visitAnnotation==PointArg=" + classDescEntity);
//        }

        return annotationVisitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        LogUtils.println(TAG, "--visitEnd--");
    }
}
