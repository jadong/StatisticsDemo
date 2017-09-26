package com.dong.statistics;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dong.statistics.app.AppConstants;
import com.jumei.tracker.annotation.PointParams;

/**
 * Created by dong on 2017/9/20.
 */
public class DemoViewHolder extends BaseView {

    private void init(Context context) {
        params.put("key", "value");

        TextView textView = new TextView(context);
        textView.setOnClickListener(new View.OnClickListener() {

            @PointParams(eventId = AppConstants.EVENT_1000, paramsName = "params")
            public void onClick(View view) {
                System.out.println("text view onclick");
            }

        });

        TextView textView2 = new TextView(context);
        textView2.setOnClickListener(new View.OnClickListener() {

            @PointParams(eventId = "WWWWWWW", paramsName = "params2")
            @Override
            public void onClick(View view) {
                System.out.println("text view onclick");
            }

        });

        new Thread(new Runnable() {

            @PointParams(eventId = "Thread", paramsName = "params2")
            @Override
            public void run() {
                System.out.println("thread run");
            }
        }).start();
    }
}
