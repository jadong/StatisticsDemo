package com.dong.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.dong.visit.SaFlag;
import com.dong.visit.SaParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/9/1.
 */
public class MainActivity extends BaseActivity {

    @SaParams({11,22,33})
    private String params = "埋点参数";

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

            @SaFlag
            @Override
            public void onClick(View v) {
                System.out.println("点击btn_go-----");
            }
        });
    }
    @SaFlag(eventId = "AA",dataId = 11)
    public void initData(int count) {
        for (int i = 0; i < count; i++) {
            list.add("--" + i);
        }
    }

    public void initData2(int count) {
        for (int i = 0; i < count; i++) {
            list2.add("--" + i);
        }
    }

}