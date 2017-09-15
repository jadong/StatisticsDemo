package com.dong.statistics;

import android.view.MotionEvent;
import android.view.View;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/9/5.
 */
public class ViewClickUtils {

    public boolean isInView(View view, MotionEvent event){
        float clickX = event.getRawX();
        float clickY = event.getRawY();
        //如下的view表示Activity中的子View或者控件
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = view.getWidth();
        int height = view.getHeight();
        if (clickX < x || clickX > (x + width) ||
                clickY < y || clickY > (y + height)) {
            return true;  //这个条件成立，则判断这个view被点击了
        }
        return false;
    }
}
