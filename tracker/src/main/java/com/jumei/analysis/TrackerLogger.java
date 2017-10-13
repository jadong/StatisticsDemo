package com.jumei.analysis;

import android.annotation.SuppressLint;
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
    private final int MAX_LEAGH = 60;
    private final String LOG_LINE = "===================================" +
            "========================================";
    private final String LOG_SIGN_01 = "*";
    private final String LOG_SIGN_02 = "          ";
    private final String LOG_SIGN = LOG_SIGN_01 + LOG_SIGN_02;


    private SimpleDateFormat dateFormat;

    @SuppressLint("SimpleDateFormat")
    public TrackerLogger() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static TrackerLogger getLogger() {
        return logger;
    }

    public void i(String tag, Object... msgs) {
        if (TextUtils.isEmpty(tag)) {
            tag = "TrackerLogger";
        }
        Thread thread = Thread.currentThread();
        StackTraceElement[] stackTrace = thread.getStackTrace();
        StackTraceElement ste = fixStackTrace(stackTrace);
        Log.i(tag, LOG_LINE);
        Log.w(tag, "*  ┌-----------------------------------------");
        Log.w(tag, "*  |调用者类:" + ste.getClassName());
        Log.w(tag, "*  |调用时间:" + dateFormat.format(new Date()));
        Log.w(tag, "*  |调用者函数:" + ste.getMethodName() + "()");
        Log.w(tag, "*  |调用者线程:" + thread.getName());
        Log.w(tag, "*  └-----------------------------------------");

        if (msgs != null) {
            for (Object msg : msgs) {
                if (msg != null) {
                    if (msg instanceof Map) {
                        Set set = ((Map) msg).entrySet();
                        for (Object o : set) {
                            fixStingLengh(tag, o.toString(), true);
                            // +"="+((Map) msg).get(o)
                        }
                    } else if (msg instanceof List) {
                        for (Object o : ((List) msg)) {
                            fixStingLengh(tag, o.toString(), true);
                        }
                    } else {
                        fixStingLengh(tag, msg.toString(), true);
                    }
                }
            }
        }
        Log.i(tag, LOG_LINE);
    }

    private StackTraceElement fixStackTrace(StackTraceElement[] stackTrace) {
        StackTraceElement stackTraceElement = stackTrace[2];
        for (int i = 0; i < stackTrace.length; i++) {
            if (i < 2) {
                continue;
            }
            StackTraceElement stackTraceElement1 = stackTrace[i];
            if (!stackTraceElement1.getClassName().contains("TrackerLogger")) {
                return stackTraceElement1;
            }
        }
        return stackTraceElement;
    }

    private void fixStingLengh(String tag, String str, boolean isFirst) {
        if (str.length() > MAX_LEAGH) {
            String before = str.substring(0, MAX_LEAGH);
            Log.i(tag, LOG_SIGN + (isFirst ? before : "    " + before));
            String after = str.substring(MAX_LEAGH, str.length());
            if (after.length() > MAX_LEAGH) {
                fixStingLengh(tag, after, false);
            } else {
                Log.i(tag, LOG_SIGN + (isFirst ? after : "    " + after));
            }
        } else {
            Log.i(tag, LOG_SIGN + (isFirst ? str : "    " + str));
        }
    }


}
