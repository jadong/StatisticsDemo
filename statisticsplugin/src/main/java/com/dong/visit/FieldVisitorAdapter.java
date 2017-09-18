package com.dong.visit;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * 字段访问适配
 * Created by dong on 2017/9/18.
 */
public class FieldVisitorAdapter extends FieldVisitor {

    private String filedName;
    private FieldEntity fieldEntity;
    private FieldEntity.DataField dataField;

    public FieldVisitorAdapter(String filedName, FieldEntity fieldEntity, FieldVisitor fieldVisitor) {
        super(Opcodes.ASM5, fieldVisitor);
        this.filedName = filedName;
        this.fieldEntity = fieldEntity;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean b) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, b);
        if (Type.getDescriptor(SaParams.class).equals(desc)) {
            dataField = new FieldEntity.DataField();
            dataField.setDataName(filedName);
            if (annotationVisitor != null) {
                annotationVisitor = new AnnotationVisitorAdapter(dataField, annotationVisitor);
            }
            fieldEntity.addDataField(dataField);
        }

        return annotationVisitor;
    }
}
