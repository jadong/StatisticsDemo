package com.dong.code;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dong on 2017/9/13.
 */
public class TimeCache {

    public static Map<String, Long> startTime = new HashMap<>();
    public static Map<String, Long> endTime = new HashMap<>();

    public static void setStartTime(String methodName, long time) {
        startTime.put(methodName, time);
    }

    public static void setEndTime(String methodName, long time) {
        endTime.put(methodName, time);
    }


    public static String getExecuteTime(String methodName) {
        long start = startTime.get(methodName);
        long end = endTime.get(methodName);
        return "method: " + methodName + " 执行时间 " + (end - start) / (1000 * 1000) + "  ms";
    }

}
