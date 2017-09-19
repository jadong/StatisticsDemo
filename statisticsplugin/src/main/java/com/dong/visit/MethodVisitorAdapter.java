package com.dong.visit;

import com.dong.visit.log.LogUtils;
import com.jumei.tracker.annotation.PointClick;
import com.jumei.tracker.annotation.PointParams;
import com.jumei.tracker.annotation.PointView;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * 方法访问者
 * onMethodEnter 、 onMethodExit中代码的生成方式：
 * 首先我们先写好java文件，然后执行javac 生成.class文件，然后执行
 * java -classpath "asm-all-5.2.jar" org.objectweb.asm.util.ASMifier TestAsm.class
 * 就可以生成asm code，然后把需要的代码拷贝过来即可。
 * <p>
 * Created by dong on 2017/9/14.
 */
public class MethodVisitorAdapter extends AdviceAdapter {

    private String TAG = "MethodVisitorAdapter";
    private boolean isView = false;
    private boolean isClick = false;
    private boolean isParams = false;
    private String methodName;
    private ClickEntity clickEntity;
    private ViewEntity viewEntity;
    private FieldEntity fieldEntity;
    private ParamsEntity paramsEntity;

    protected MethodVisitorAdapter(String methodName, FieldEntity fieldEntity, MethodVisitor methodVisitor, int access, String name, String desc) {
        super(Opcodes.ASM5, methodVisitor, access, name, desc);
        this.methodName = methodName;
        this.fieldEntity = fieldEntity;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        LogUtils.println(TAG, "---visitCode---");
    }

    /**
     * 当我们在某个方法写上注解的时候，它会执行这个方法，
     * 我们可以在这个方法判断是否是我们注解做一些事情，比如重置一个标志位；
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        LogUtils.println(TAG, "---visitAnnotation---" + desc);
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);

        if (Type.getDescriptor(PointClick.class).equals(desc)) {
            isClick = true;
            if (annotationVisitor != null) {
                clickEntity = new ClickEntity();
                annotationVisitor = new AnnotationVisitorAdapter(clickEntity, annotationVisitor);
            }
        } else if (Type.getDescriptor(PointView.class).equals(desc)) {
            isView = true;
            if (annotationVisitor != null) {
                viewEntity = new ViewEntity();
                annotationVisitor = new AnnotationVisitorAdapter(viewEntity, annotationVisitor);
            }
        } else if (Type.getDescriptor(PointParams.class).equals(desc)) {
            isParams = true;
            if (annotationVisitor != null) {
                paramsEntity = new ParamsEntity();
                annotationVisitor = new AnnotationVisitorAdapter(paramsEntity, annotationVisitor);
            }
        }

        return annotationVisitor;
    }

    /**
     * 方法进入调用
     */
    @Override
    protected void onMethodEnter() {
        LogUtils.println(TAG, "---onMethodEnter---");
        if (isView && viewEntity != null && fieldEntity != null) {
            //记录方法开始时间
            printStartTime();

            LogUtils.println(TAG, "=====onMethodEnter====浏览事件===viewEntity=" + viewEntity + "==fieldEntity=" + fieldEntity);

            //浏览事件埋点
            FieldEntity.DataField dataField = fieldEntity.getDataField(viewEntity.getDataId());
            if (dataField == null) {
                return;
            }
            mv.visitLdcInsn(viewEntity.getEventId());
            mv.visitVarInsn(ASTORE, 1);
            mv.visitLdcInsn(fieldEntity.getClassFullName());
            mv.visitVarInsn(ASTORE, 2);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, fieldEntity.getClassFullName(), dataField.getDataName(), dataField.getDataType());
            mv.visitMethodInsn(INVOKESTATIC, "com/jumei/analysis/Tracker", "onView", "(Ljava/lang/String;Ljava/lang/String;" + dataField.getDataType() + ")V", false);


        } else if (isParams && paramsEntity != null) {

            LogUtils.println(TAG, "=====onMethodEnter====点击事件===paramsEntity=" + paramsEntity);

            //点击事件埋点

            mv.visitLdcInsn(paramsEntity.getEventId());
            mv.visitVarInsn(ASTORE, 1);
            mv.visitLdcInsn(paramsEntity.getClassFullName());
            mv.visitVarInsn(ASTORE, 2);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, paramsEntity.getClassFullName(), paramsEntity.getParamsName(), paramsEntity.getParamsType());
            mv.visitMethodInsn(INVOKESTATIC, "com/jumei/analysis/Tracker", "onClick", "(Ljava/lang/String;Ljava/lang/String;" + paramsEntity.getParamsType() + ")V", false);

        }
    }


    /**
     * 方法结束时调用
     */
    @Override
    protected void onMethodExit(int i) {
        LogUtils.println(TAG, "---onMethodExit---");
        if (isView) {

            //记录并输出方法耗时
            printEndTime();
        }
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        LogUtils.println(TAG, "---visitEnd---");
    }

    private void printStartTime() {
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("========start=========" + methodName);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);

        mv.visitLdcInsn(methodName);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "com/dong/code/TimeCache", "setStartTime",
                "(Ljava/lang/String;J)V", false);
    }

    private void printEndTime() {
        mv.visitLdcInsn(methodName);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "com/dong/code/TimeCache", "setEndTime",
                "(Ljava/lang/String;J)V", false);

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(methodName);
        mv.visitMethodInsn(INVOKESTATIC, "com/dong/code/TimeCache", "getExecuteTime",
                "(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("========end=========");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);
    }
}
