package com.dong.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dong.visit.SaFlag;
import com.jumei.analysis.Tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/9/1.
 */
public class MainActivity extends BaseActivity {

    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData(1000);
        initData2(10000);
        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> a = new HashMap<>();
                a.put("name","xiaoming");
                List<String> b = new ArrayList<>();
                b.add("haha");
                b.add("haha啊啊啊啊");
                Tracker.onClick("100",MainActivity.this.getLocalClassName(),
                        new User("小明",18)
                );
            }
        });
    }

    @SaFlag
    public void initData(int count){
        for (int i = 0; i < count; i++) {
            list.add("--"+i);
        }
    }

    @SaFlag
    public void initData2(int count){
        for (int i = 0; i < count; i++) {
            list2.add("--"+i);
        }
    }

}