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
    private FieldEntity.DataField dataField;
    private ParamsEntity paramsEntity;

//    public AnnotationVisitorAdapter(FieldEntity.DataField dataField, AnnotationVisitor annotationVisitor) {
//        super(Opcodes.ASM5, annotationVisitor);
//        this.dataField = dataField;
//        LogUtils.println(TAG, "--AnnotationVisitorAdapter()--dataField=" + dataField);
//    }

    public AnnotationVisitorAdapter(ParamsEntity paramsEntity, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.paramsEntity = paramsEntity;
        LogUtils.println(TAG, "--AnnotationVisitorAdapter()--paramsEntity=" + paramsEntity);
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
        LogUtils.println(TAG, "--visit--name=" + name + "--value=" + value);

        if (dataField != null && name.equals("value")) {//@PointArg
            if (value instanceof int[]) {
                dataField.setDataIds((int[]) value);
            }
        }

        //@PointParams
        if (paramsEntity != null) {
            if (name.equals("eventId")) {
                paramsEntity.setEventId(value.toString());
            } else if (name.equals("paramsName")) {
                paramsEntity.setParamsName(value.toString());
            }
        }

    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        LogUtils.println(TAG, "---visitEnd---paramsEntity=" + paramsEntity);
    }
}
