package com.dong.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.jumei.analysis.Tracker;
import com.jumei.tracker.annotation.PointArg;
import com.jumei.tracker.annotation.PointView;

import java.util.ArrayList;
import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/9/1.
 */
public class MainActivity extends BaseActivity {

    @PointArg({11, 33})
    private String params = "埋点参数";

    @PointArg({22})
    private String params22 = "埋点参数2222";

    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private Button btn_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_go = (Button) findViewById(R.id.btn_go);

        initData(1000);

        initData2(10000);

        btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("点击btn_go-----");
                Tracker.onClick("btn_go",getLocalClassName(),new User("xiaoming",19));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIntent().putExtra("scheme","kayo://page/main?a=111&b=222&scheme=http%3a%2f%2fwww.baidu.com");
        Tracker.onAttached(getLocalClassName(),getIntent());
    }

    @PointView(eventId = "AA", dataId = 22)
    public void initData(int count) {
        for (int i = 0; i < count; i++) {
            list.add("--" + i);
        }
    }

    @PointView(eventId = "BB", dataId = 33)
    public void initData2(int count) {
        for (int i = 0; i < count; i++) {
            list2.add("--" + i);
        }
    }

}