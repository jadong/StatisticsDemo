package com.jumei.analysis;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kayo on 17/8/29.
 * logger
 */

public class TrackerLogger {

    private static final TrackerLogger logger = new TrackerLogger();
    private final String LOG_LINE = "===================================" +
            "========================================";


    private SimpleDateFormat dateFormat;
    public TrackerLogger(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static TrackerLogger getLogger(){
        return logger;
    }

    public void i(Object... msgs){
        i("",msgs);
    }

    public void i(String tag,Object... msgs){

        if (TextUtils.isEmpty(tag)){
            tag = "TrackerLogger";
        }
        Thread thread = Thread.currentThread();
        StackTraceElement[] stackTrace = thread.getStackTrace();
        StackTraceElement ste = fixStackTrace(stackTrace);
        Log.i(tag,LOG_LINE);
        Log.i(tag,"* 调用者类:"+ste.getClassName());
        Log.i(tag,"* 调用时间:"+dateFormat.format(new Date()));
        Log.i(tag,"* 调用者函数:"+ste.getMethodName()+"()");
        Log.i(tag,"* 调用者线程:"+thread.getName());

        if (msgs != null) {
            for (Object msg : msgs) {
                if (msg != null) {
                    if (msg instanceof Map){
                        Set set = ((Map) msg).entrySet();
                        for (Object o : set) {
                            Log.i(tag,"*          "+o
                                   // +"="+((Map) msg).get(o)
                            );
                        }
                    }else if (msg instanceof List){
                        for (Object o : ((List) msg)) {
                            Log.i(tag,"*          "+o);
                        }
                    }else {
                        Log.i(tag,"*          "+msg);
                    }
                }
            }
        }
        Log.i(tag,LOG_LINE);
    }

    private StackTraceElement fixStackTrace(StackTraceElement[] stackTrace){
        StackTraceElement stackTraceElement = stackTrace[2];
        for (int i = 0; i < stackTrace.length; i++) {
            if (i <2){
                continue;
            }
            StackTraceElement stackTraceElement1 = stackTrace[i];
            if (!stackTraceElement1.getClassName().contains("TrackerLogger")){
                return stackTraceElement1;
            }
        }
        return stackTraceElement;

    }




}
