package com.jumei.analysis;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kayo on 17/9/18.
 */
public class Tracker {

    private ArgCompat argCompat;
    private Sender sender;
    private Context appContext;
    private static Tracker tracker;

    private Tracker(Context context) {
        this.appContext = context;
        argCompat = new ArgCompat();
        sender = new Sender();
        new Config(context);
    }

    /**
     * 初始化 tracker
     *
     * @param appContext
     */
    public static Tracker init(Context appContext) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#init()");
        if (tracker == null) {
            synchronized (Tracker.class) {
                if (tracker == null) {
                    tracker = new Tracker(appContext);
                }
            }
        }
        return tracker;
    }

    /**
     * 添加基础参数
     *
     * @param baseArgus 基础数据参数
     */
    public static void baseArgs(Map<String, String> baseArgus) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#setBaseArgus()");
        if (tracker != null) {
            if (tracker.argCompat != null) {
                tracker.argCompat.setBaseArgs(baseArgus);
            } else {
                tracker.argCompat = new ArgCompat(baseArgus);
            }
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    /**
     * 普通点击 数据
     *
     * @param view
     * @param object 数据类
     */
    public static void onClick(View view, String className) {

    }

    public static void onCTRClick(View view,String className) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onClick()");
        if (tracker != null) {
            tracker.onTrack("", className, null);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onCTRView(View view,String className) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onView()");

    }

    /**
     * 绑定当前视图
     *
     * @param context 上下文对象
     */
    public static void onActivityAttached(Context context) {

        TrackerLogger.getLogger().i("Tracker", "Tracker#onAttached()");
        if (tracker != null && tracker.argCompat != null) {
            tracker.argCompat.onAttached(context);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onFragmentAttached(Fragment fragment){}

    public static void onFragmentAttached(android.app.Fragment fragment){}

    /**
     * class被创建时调用的
     *
     * @param object 数据
     */
    public static void onClassCreate(String className,Object object) {

    }

    /**
     * 埋点调用
     * @param view
     */
    public static void onTrack(View view, String className, Object object){}


    /**
     * 为view添加tag参数值
     * @param view
     * @param key
     * @param value
     */
    @SuppressWarnings("unchecked")
    public static void appendParam(View view, String key, String value) {
//        TrackerLogger.getLogger().i("appendParams",key+"="+value);
        if (view == null
                || (QUtils.stringIsEmpty(key) && QUtils.stringIsEmpty(value))){
            return;
        }

        Object tag = view.getTag(Content.tag_id);
        Map<String,String> values;
        if (tag != null && tag instanceof Map) {
            values = (Map) tag;
            TrackerLogger.getLogger().i("appendParam","已有数据",values);
        }else {
            values = new HashMap<>();
        }
        values.put(key,value);
        view.setTag(Content.tag_id,values);
    }

    /**
     * 为view添加tag数据结构值
     * @param view
     * @param object
     */
    public static void appendParam(View view,Object object){}




    //动作开启
    private void onTrack(String eventId, String className, Object object) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onTrack()");
        if (argCompat != null && sender != null) {
            Map<String, String> convert = argCompat.convert(eventId, className, object);
            sender.send(convert);
        }
    }


}
