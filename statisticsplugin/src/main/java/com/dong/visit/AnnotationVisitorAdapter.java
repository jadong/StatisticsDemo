package com.dong.visit;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 注解访问适配
 * Created by dong on 2017/9/18.
 */
public class AnnotationVisitorAdapter extends AnnotationVisitor {

    private ParamsEntity.DataField dataField;

    public AnnotationVisitorAdapter(ParamsEntity.DataField dataField, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.dataField = dataField;
    }

    @Override
    public void visit(String name, Object value) {
        if (dataField != null) {//@SaParams
            dataField.setDataId((String[]) value);
        }
        super.visit(name, value);
    }
}
