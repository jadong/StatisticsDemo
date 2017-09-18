package com.jumei.analysis;

import android.content.Context;
import android.content.Intent;

import java.util.Map;

/**
 * Created by kayo on 17/9/18.
 */
public class Tracker {

    private ArgCompat argCompat;
    private Sender sender;
    private Context context;
    private static Tracker tracker;

    private Tracker(Context context) {
        this.context = context;
        argCompat = new ArgCompat();
        sender = new Sender();
    }

    /**
     * 初始化 tracker
     *
     * @param context
     */
    public static Tracker init(Context context) {
        TrackerLogger.getLogger().i("Tracker","Tracker#init()");
        if (tracker == null) {
            synchronized (Tracker.class) {
                if (tracker == null) {
                    tracker = new Tracker(context);
                }
            }
        }
        return tracker;
    }

    public static void onClick(String eventId, String className, Object object) {
        TrackerLogger.getLogger().i("Tracker","Tracker#onClick()");
        if (tracker != null) {
            tracker.onTrack(eventId, className, object);
        }else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onView(String eventId, String className, Object object) {
        TrackerLogger.getLogger().i("Tracker","Tracker#onView()");
        if (tracker != null) {
            tracker.onTrack(eventId, className, object);
        }else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    /**
     * 绑定当前视图
     *
     * @param className
     * @param intent
     */
    public static void onAttached(String className, Intent intent) {
        TrackerLogger.getLogger().i("Tracker","Tracker#onAttached()");
        if (tracker != null && tracker.argCompat != null) {
            tracker.argCompat.onAttached(className, intent);
        }else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    /**
     * 添加基础参数
     *
     * @param baseArgus 基础数据参数
     */
    public void setBaseArgs(Map<String, String> baseArgus) {
        TrackerLogger.getLogger().i("Tracker","Tracker#setBaseArgus()");
        if (argCompat != null) {
            argCompat.setBaseArgs(baseArgus);
        } else {
            argCompat = new ArgCompat(baseArgus);
        }
    }

    /**
     * 时间被触发
     *
     * @param eventId
     * @param className
     * @param object
     */
    public void onTrack(String eventId, String className, Object object) {
        TrackerLogger.getLogger().i("Tracker","Tracker#onTrack()");
        if (argCompat != null && sender != null) {
            Map<String, String> convert = argCompat.convert(eventId, className, object);
            sender.send(convert);
        }
    }


}
