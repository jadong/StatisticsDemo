package com.dong.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dong.visit.SaFlag;

import java.util.ArrayList;
import java.util.List;

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