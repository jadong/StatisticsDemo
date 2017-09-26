package com.dong.code;

import android.view.View;

import com.dong.statistics.MainActivity;
import com.jumei.analysis.Tracker;

/**
 * Created by dong on 2017/9/14.
 */
public class GenerateBytecode {

    private MainActivity mainActivity;

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
        Tracker.onClick(view,"RRRRRRR");
    }

}
