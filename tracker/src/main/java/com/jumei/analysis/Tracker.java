package com.jumei.analysis;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

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
    public void setBaseArgs(Map<String, String> baseArgus) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#setBaseArgus()");
        if (argCompat != null) {
            argCompat.setBaseArgs(baseArgus);
        } else {
            argCompat = new ArgCompat(baseArgus);
        }
    }

    /**
     * 普通点击 数据
     *
     * @param view
     * @param object 数据类
     */
    public static void onClick(View view, String actionClassName, Object object) {

    }

    public static void onCTRClick(String className, Object object) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onClick()");
        if (tracker != null) {
            tracker.onTrack("", className, object);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onCTRView(String className, Object object) {
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
     * 需要在创建时调用的
     *
     * @param object 数据
     */
    public static void onObjectCreate(String className,Object object) {

    }

    /**
     * 埋点调用
     * @param view
     */
    public static void onTrack(View view, String className, Object object){}


    /**
     * 有参数埋点调用
     *
     * @param eventId
     * @param className
     * @param object
     */
    public void onTrack(String eventId, String className, Object object) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onTrack()");
        if (argCompat != null && sender != null) {
            Map<String, String> convert = argCompat.convert(eventId, className, object);
            sender.send(convert);
        }
    }

    /**
     * 为view添加tag参数值
     * @param view
     * @param key
     * @param value
     */
    public static void appendParam(View view, String key, Object value) {
        TrackerLogger.getLogger().i("appendParams",key+"="+value);
        Object tag = view.getTag();
        String values = "";
        if (tag != null) {
            values = String.valueOf(tag);
        }TrackerLogger.getLogger().i("values","values =>> "+values);
        String var1 = key + "=" + value;
        String var2 = "|" + var1;
        String var3 = var1 + "|";
        if (QUtils.oneIn(values,var1,var2,var3)){
            TrackerLogger.getLogger().i("appendParams","  包含以下值",key+"="+value);
            return;
        }
        if (values.contains("|")){
            String[] split = values.split("\\|");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (s.contains(key)){

                }else {

                }
                if (s.contains("=")){
                    String[] split1 = s.split("=");
                    if (QUtils.stringEquals(split1[0],key)){

                    }

                    if (i==0){

                    }else {

                    }
                }



            }
        }else {
            if (QUtils.stringIsEmpty(values)){
                values = var1;
            }else {
                values += var2;
            }
        }
        view.setTag(values);
    }

    /**
     * 为view添加tag数据结构值
     * @param view
     * @param object
     */
    public static void appendObject(View view,Object object){}


}
