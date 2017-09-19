package com.dong.visit;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 注解访问适配
 * Created by dong on 2017/9/18.
 */
public class AnnotationVisitorAdapter extends AnnotationVisitor {

    private FieldEntity.DataField dataField;
    private ClickEntity clickEntity;
    private ViewEntity viewEntity;

    public AnnotationVisitorAdapter(FieldEntity.DataField dataField, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.dataField = dataField;
    }

    public AnnotationVisitorAdapter(ClickEntity clickEntity, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.clickEntity = clickEntity;
    }

    public AnnotationVisitorAdapter(ViewEntity viewEntity, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.viewEntity = viewEntity;
    }

    @Override
    public void visit(String name, Object value) {
        if (dataField != null && name.equals("value")) {//@PointArg
            if (value instanceof int[]) {
                dataField.setDataId((int[]) value);
            }
        }

        //@PointClick
        if (clickEntity != null) {
            if (name.equals("dataId")) {
                clickEntity.setDataId((int) value);
            } else if (name.equals("eventId")) {
                clickEntity.setEventId(value.toString());
            }
        }

        //@PointView
        if (viewEntity != null) {
            if (name.equals("dataId")) {
                viewEntity.setDataId((int) value);
            } else if (name.equals("eventId")) {
                viewEntity.setEventId(value.toString());
            }
        }

        super.visit(name, value);
    }
}
