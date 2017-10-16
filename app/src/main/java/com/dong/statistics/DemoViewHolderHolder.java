package com.dong.statistics;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jumei.tracker.annotation.CTRClick;

/**
 * Created by dong on 2017/9/20.
 */
public class DemoViewHolderHolder extends BaseViewHolder {

    public DemoViewHolderHolder(Context context) {
        super(context);
    }

    private void init(Context context) {

        TextView textView = new TextView(context);
        textView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                System.out.println("text view onclick");
            }

        });

        TextView textView2 = new TextView(context);
        textView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("text view onclick");
            }

        });

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("thread run");
            }
        }).start();
    }

    @CTRClick("params")
    public void onCTRClick(){
        System.out.println("onCTRView");
    }


    public void onCTRView(){
        new Thread(new Runnable() {

            @CTRClick("viewParams")
            @Override
            public void run() {
                System.out.println("onCTRView");
            }
        }).start();
    }
}
