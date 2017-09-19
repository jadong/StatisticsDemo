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
    private ClickEntity clickEntity;
    private ViewEntity viewEntity;
    private ParamsEntity paramsEntity;

    public AnnotationVisitorAdapter(FieldEntity.DataField dataField, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.dataField = dataField;
        LogUtils.println(TAG, "--AnnotationVisitorAdapter()--dataField=" + dataField);
    }

    public AnnotationVisitorAdapter(ClickEntity clickEntity, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.clickEntity = clickEntity;
        LogUtils.println(TAG, "--AnnotationVisitorAdapter()--clickEntity=" + clickEntity);
    }

    public AnnotationVisitorAdapter(ViewEntity viewEntity, AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
        this.viewEntity = viewEntity;
        LogUtils.println(TAG, "--AnnotationVisitorAdapter()--viewEntity=" + viewEntity);
    }

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

        if (paramsEntity != null) {
            if (name.equals("eventId")) {
                paramsEntity.setEventId(value.toString());
            } else if (name.equals("paramsName")) {
                paramsEntity.setParamsName(value.toString());
            } else if (name.equals("paramsType")) {
                paramsEntity.setParamsType(value.toString());
            } else if (name.equals("classFullName")) {
                paramsEntity.setClassFullName(value.toString());
            }
        }

    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        LogUtils.println(TAG, "---visitEnd--dataField=" + dataField + "---clickEntity=" + clickEntity + "---=viewEntity=" + viewEntity);
        LogUtils.println(TAG, "---visitEnd---paramsEntity=" + paramsEntity);
    }
}
