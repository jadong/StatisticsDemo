package com.jumei.analysis;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
     */
    public static void onClick(View view, String className) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onClick()");
        if (tracker != null) {
            tracker.parseView(view,className,null);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }

    }

    public static void onCTRClick(View view,String className,Object object) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onCTRClick()");
        if (tracker != null) {
            tracker.onCTR(Content.VIEW_MATERIAL,view,className,object);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onCTRView(View view,String className,Object object) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onCTRView()");
        if (tracker != null) {
            tracker.onCTR(Content.VIEW_MATERIAL,view,className,object);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }

    }

    private void onCTR(String event_id,View view,String className,Object object){
        Map map = null;
        if (view != null){
            Object tag = view.getTag(Content.tag_id);
            if (tag != null){
                map = (Map) tag;
            }
            if (object == null){
                object = view.getTag(Content.object_id);
            }
        }
        tracker.go(event_id, className,map, object);
    }

    /**
     * 绑定当前视图
     *
     * @param context 上下文对象
     */
    public static void onActivityAttached(Context context) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#onActivityAttached()");
        if (tracker != null && tracker.argCompat != null) {
            tracker.argCompat.onAttached(context);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onFragmentAttached(Fragment fragment){
        TrackerLogger.getLogger().i("Tracker", "Tracker#onFragmentAttached()");
        if (tracker != null && tracker.argCompat != null) {
            View view = fragment.getView();
            String simpleName = fragment.getClass().getSimpleName();
            tracker.onFragmentAttached(view,simpleName);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onFragmentAttached(android.app.Fragment fragment){
        TrackerLogger.getLogger().i("Tracker", "Tracker#onFragmentAttached()");
        if (tracker != null && tracker.argCompat != null) {
            View view = fragment.getView();
            String simpleName = fragment.getClass().getSimpleName();
            tracker.onFragmentAttached(view,simpleName);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    private void onFragmentAttached(View view,String className){
        String eventId = QUtils.getEventId(view);
        Map<String,String> tagParams = QUtils.getTagParams(view);
        Object object = QUtils.getObject(view);
        tracker.go(eventId,className,tagParams,object);
    }

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
    public static void onTrack(View view, String className, Object object){
        TrackerLogger.getLogger().i("Tracker", "Tracker#onTrack()  with view");
        if (tracker != null) {
            tracker.parseView(view, className, object);
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    public static void onTrack(String eventId,String className, Object object){
        TrackerLogger.getLogger().i("Tracker", "Tracker#onTrack()  without view");
        if (tracker != null) {
            if (object instanceof Map){
                Map<String,String> temp = new HashMap<>();
                Map a = (Map) object;
                Set set = a.keySet();
                if (set != null) {
                    for (Object o : set) {
                        Object o1 = a.get(o);
                        temp.put(String.valueOf(o),String.valueOf(o1));
                    }
                    tracker.go(eventId,className,temp,null);
                }
            }else {
                tracker.go(eventId,className,null,object);
            }
        } else {
            throw new IllegalArgumentException("the tracker need to invoking " +
                    "init(Content) method , place check it!");
        }
    }

    /**
     * 为view添加tag参数值
     * @param view
     * @param key
     * @param value
     */
    @SuppressWarnings("unchecked")
    public static void appendParam(View view, String key, String value) {
//        TrackerLogger.getLogger().i("appendParam",key+"="+value);
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
    public static void appendParam(View view,Object object){
        TrackerLogger.getLogger().i("appendParam","添加数据体");
        if (view != null){
            view.setTag(Content.object_id,object);
        }
    }


    //解析view数据
    private void parseView(View view,String className,Object object){
        Map map = null;
        if (view != null){
            Object tag = view.getTag(Content.tag_id);
            if (tag != null){
                map = (Map) tag;
            }
            if (object == null) {
                object = view.getTag(Content.object_id);
            }

        }
        tracker.go("", className,map, object);
    }


    //动作开启
    private void go(String eventId, String className,Map<String,String> tagParams ,Object object) {
        TrackerLogger.getLogger().i("Tracker", "Tracker#go()");
        if (argCompat != null && sender != null) {
            Map<String, String> convert = argCompat.convert(eventId, className, tagParams,object);
            sender.send(convert);
        }
    }


}
