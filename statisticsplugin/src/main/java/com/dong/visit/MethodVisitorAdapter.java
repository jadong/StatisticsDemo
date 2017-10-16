package com.dong.visit;

import com.dong.visit.log.LogUtils;
import com.jumei.tracker.annotation.CTRClick;
import com.jumei.tracker.annotation.CTRView;
import com.jumei.tracker.annotation.ExecuteTime;

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
    private boolean isExecuteTime = false;
    private String methodName;
    private ClassDescEntity classDescEntity;
    private AnnotationParams annotationParams;

    protected MethodVisitorAdapter(ClassDescEntity classDescEntity, MethodVisitor methodVisitor, int access, String name, String desc) {
        super(Opcodes.ASM5, methodVisitor, access, name, desc);
        this.methodName = name;
        this.classDescEntity = classDescEntity;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        LogUtils.println(TAG, "---visitCode---");
    }

    @Override
    public void visitParameter(String s, int i) {
        super.visitParameter(s, i);
        LogUtils.println(TAG, "---visitParameter---" + s + "---" + i);
    }

    /**
     * 当我们在某个方法写上注解的时候，它会执行这个方法，
     * 我们可以在这个方法判断是否是我们注解做一些事情，比如重置一个标志位；
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        LogUtils.println(TAG, "---visitAnnotation---" + desc);
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);

        if (Type.getDescriptor(ExecuteTime.class).equals(desc)) {
            isExecuteTime = true;
        } else if (Type.getDescriptor(CTRClick.class).equals(desc)) {
            annotationParams = new AnnotationParams();
            annotationParams.setCTRClick(true);
            if (annotationVisitor != null) {
                annotationVisitor = new AnnotationVisitorAdapter(annotationParams, annotationVisitor);
            }
        } else if (Type.getDescriptor(CTRView.class).equals(desc)) {
            annotationParams = new AnnotationParams();
            annotationParams.setCTRView(true);
            if (annotationVisitor != null) {
                annotationVisitor = new AnnotationVisitorAdapter(annotationParams, annotationVisitor);
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
        if (isExecuteTime) {
            //记录方法开始时间
            printStartTime();
        }

    }

    /**
     * 方法结束时调用
     */
    @Override
    protected void onMethodExit(int i) {
        LogUtils.println(TAG, "---onMethodExit---");
        if (isExecuteTime) {

            //记录并输出方法耗时
            printEndTime();
        }

        handlerCTR();
    }

    private void handlerCTR() {
        if (annotationParams != null) {
            LogUtils.println(TAG, "=====handlerCTR====annotationParams===" + annotationParams);
            if (classDescEntity.getOuterClassRefName() != null) {//内部类中调用的字节码

                //第一个参数名称默认为itemView
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, classDescEntity.getClassFullName(), classDescEntity.getOuterClassRefName(), classDescEntity.getOuterClassRefType());
                mv.visitFieldInsn(GETFIELD, classDescEntity.getOuterClassFullName(), "itemView", "Landroid/view/View;");

                //第二个参数为当前类名称字符串
                mv.visitLdcInsn(classDescEntity.getClassFullName());

                //第三个参数为埋点数据对象
                if (annotationParams.getParamsName() == null || annotationParams.getParamsName().equals("")) {
                    mv.visitInsn(ACONST_NULL);//参数名称为空
                } else {
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, classDescEntity.getClassFullName(), classDescEntity.getOuterClassRefName(), classDescEntity.getOuterClassRefType());
                    mv.visitFieldInsn(GETFIELD, classDescEntity.getOuterClassFullName(), annotationParams.getParamsName(), "Ljava/lang/Object;");
                }

            } else {//非内部类中调用的字节码

                //第一个参数名称默认为itemView
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, classDescEntity.getClassFullName(), "itemView", "Landroid/view/View;");

                //第二个参数为当前类名称字符串
                if (annotationParams.getParamsName() == null || annotationParams.getParamsName().equals("")) {
                    mv.visitInsn(ACONST_NULL);//参数名称为空
                } else {
                    mv.visitLdcInsn(classDescEntity.getClassFullName());
                }

                //第三个参数为埋点数据对象
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, classDescEntity.getClassFullName(), annotationParams.getParamsName(), "Ljava/lang/Object;");
            }

            String mN = "";
            if (annotationParams.isCTRClick()) {
                mN = "onCTRClick";//CTR点击事件埋点
            } else if (annotationParams.isCTRView()) {
                mN = "onCTRView";//CTR浏览事件埋点
            }
            if (!mN.equals("")) {
                LogUtils.println(TAG, "=====handlerCTR====" + mN);
                mv.visitMethodInsn(INVOKESTATIC, "com/jumei/analysis/Tracker", mN, "(Landroid/view/View;Ljava/lang/String;Ljava/lang/Object;)V", false);
            }
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
