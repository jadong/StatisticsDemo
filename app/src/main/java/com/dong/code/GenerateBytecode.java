package com.dong.code;

import android.view.View;

import com.dong.statistics.MainActivity;
import com.jumei.analysis.Tracker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dong on 2017/9/14.
 */
public class GenerateBytecode {

    private MainActivity mainActivity;
    private View itemView;
    private Map<String, String> dataObj = new HashMap<>();

    public GenerateBytecode(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void code() {

        /**
         * mv.visitLdcInsn("EEEE");
         mv.visitLdcInsn("CCCC");
         mv.visitVarInsn(ALOAD, 0);
         mv.visitFieldInsn(GETFIELD, "com/dong/code/GenerateBytecode", "mainActivity", "Lcom/dong/statistics/MainActivity;");
         mv.visitFieldInsn(GETFIELD, "com/dong/statistics/MainActivity", "params", "Ljava/lang/String;");
         mv.visitMethodInsn(INVOKESTATIC, "com/jumei/analysis/Tracker", "onClick", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", false);

         */
        //Tracker.onClick("EEEE", "CCCC", this.mainActivity.params);
    }

    public void onClick(View view) {

    }

    public void onCTRClick() {
        /**
         * mv.visitVarInsn(ALOAD, 0);
         mv.visitFieldInsn(GETFIELD, "com/dong/code/GenerateBytecode", "itemView", "Landroid/view/View;");
         mv.visitLdcInsn("className");
         mv.visitInsn(ACONST_NULL);
         mv.visitMethodInsn(INVOKESTATIC, "com/jumei/analysis/Tracker", "onCTRClick", "(Landroid/view/View;Ljava/lang/String;Ljava/lang/Object;)V", false);

         */
        Tracker.onCTRClick(itemView, "className", null);
    }

    public void onCTRView() {
        /**
         * mv.visitVarInsn(ALOAD, 0);
         mv.visitFieldInsn(GETFIELD, "com/dong/code/GenerateBytecode", "itemView", "Landroid/view/View;");
         mv.visitLdcInsn("className");
         mv.visitVarInsn(ALOAD, 0);
         mv.visitFieldInsn(GETFIELD, "com/dong/code/GenerateBytecode", "dataObj", "Ljava/util/Map;");
         mv.visitMethodInsn(INVOKESTATIC, "com/jumei/analysis/Tracker", "onCTRClick", "(Landroid/view/View;Ljava/lang/String;Ljava/lang/Object;)V", false);

         */
        Tracker.onCTRClick(itemView, "className", dataObj);
    }

}
