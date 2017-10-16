package com.dong.statistics.app;

import android.app.Application;

import com.jumei.analysis.Tracker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dong on 2017/9/13.
 */
public class SAApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Map<String,String> baseArgus = new HashMap<>();
        baseArgus.put("base_arg1","123");
        baseArgus.put("base_arg2","234");
        baseArgus.put("base_arg3","345");
        Tracker.init(this.getApplicationContext());
        Tracker.baseArgs(baseArgus);

    }
}
