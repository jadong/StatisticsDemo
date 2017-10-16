package com.dong.visit;

import com.dong.visit.log.LogUtils;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 注解访问适配
 * Created by dong on 2017/9/18.
 */
public class AnnotationVisitorAdapter extends AnnotationVisitor {

    private String TAG = "AnnotationVisitorAdapter";
    private ClassDescEntity.DataField dataField;
    private AnnotationParams annotationParams;

//    public AnnotationVisitorAdapter(ClassDescEntity.DataField dataField, AnnotationVisitor annotationVisitor) {
//        super(Opcodes.ASM5, annotationVisitor);
//        this.dataField = dataField;
//        LogUtils.println(TAG, "--AnnotationVisitorAdapter()--dataField=" + dataField);
//    }

    public AnnotationVisitorAdapter(AnnotationParams annotationParams, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.annotationParams = annotationParams;
        LogUtils.println(TAG, "--AnnotationVisitorAdapter()--annotationParams=" + annotationParams);
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
        LogUtils.println(TAG, "--visit--name=" + name + "--value=" + value);

        if (annotationParams != null) {
            if (name.equals("eventId")) {
                annotationParams.setEventId(value.toString());
            } else if (name.equals("value")) {
                annotationParams.setParamsName(value.toString());
            }
        }

    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        LogUtils.println(TAG, "---visitEnd---annotationParams=" + annotationParams);
    }
}
