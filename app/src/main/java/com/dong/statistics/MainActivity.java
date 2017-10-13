package com.dong.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.jumei.analysis.Tracker;
import com.jumei.tracker.annotation.ExecuteTime;
import com.jumei.tracker.annotation.PointParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/9/1.
 */
public class MainActivity extends BaseActivity {


    private String params = "埋点参数";

    private String params22 = "埋点参数2222";

    private String params33 = "埋点参数3333";

    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private Button btn_go;
    private Button btn_jump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_go = (Button) findViewById(R.id.btn_go);
        btn_jump = (Button) findViewById(R.id.btn_jump);
        initData(1000);

        initData2(10000);
        btn_go.setTag("en=aaa|b=45667");
        btn_go.setOnClickListener(new View.OnClickListener() {

            @PointParams(eventId = "AAAA", paramsName = "params")
            @Override
            public void onClick(View v) {
                System.out.println("点击btn_go-----");

                Tracker.appendParam(btn_go,"en","0x4433234");
                Tracker.onCTRClick("",new User("小明",18));


            }
        });

        btn_jump.setOnClickListener(new View.OnClickListener() {

            @PointParams(eventId = "DDDD", paramsName = "params")
            @Override
            public void onClick(View v) {
                System.out.println("btn_jump-----");
            }
        });

    }

    @PointParams(eventId = "BBBB", paramsName = "params22")
    public void initData(int count) {
        for (int i = 0; i < count; i++) {
            list.add("--" + i);
        }
    }

    @PointParams(eventId = "FFFFF", paramsName = "params33")
    @ExecuteTime
    public void initData2(int count) {
        for (int i = 0; i < count; i++) {
            list2.add("--" + i);
        }
    }

}