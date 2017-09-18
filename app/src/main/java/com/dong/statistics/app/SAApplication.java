package com.dong.statistics.app;

import android.app.Application;

<<<<<<< HEAD
import com.jumei.analysis.Tracker;

import java.util.HashMap;
import java.util.Map;

=======
>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
/**
 * Created by dong on 2017/9/13.
 */
public class SAApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
<<<<<<< HEAD
        Map<String,String> baseArgus = new HashMap<>();
        baseArgus.put("argu1","123");
        baseArgus.put("argu2","123");
        baseArgus.put("argu3","123");
        Tracker.init(this.getApplicationContext()).setBaseArgs(baseArgus);
=======
>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
    }
}
