package com.dong.visit;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 注解访问适配
 * Created by dong on 2017/9/18.
 */
public class AnnotationVisitorAdapter extends AnnotationVisitor {

    private FieldEntity.DataField dataField;
    private MethodEntity methodEntity;

    public AnnotationVisitorAdapter(FieldEntity.DataField dataField, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.dataField = dataField;
    }

    public AnnotationVisitorAdapter(MethodEntity methodEntity, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.methodEntity = methodEntity;
    }

    @Override
    public void visit(String name, Object value) {
        if (dataField != null && name.equals("value")) {//@SaParams
            if (value instanceof int[]) {
                dataField.setDataId((int[]) value);
            }
        }

        if (methodEntity != null) {
            if (name.equals("dataId")) {
                methodEntity.setDataId((int) value);
            } else if (name.equals("eventId")) {
                methodEntity.setEventId(value.toString());
            }
        }

        super.visit(name, value);
    }
}
