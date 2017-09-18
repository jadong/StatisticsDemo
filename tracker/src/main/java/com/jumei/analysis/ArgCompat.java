package com.jumei.analysis;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kayo on 17/9/18.
 * 参数包装类
 */
public class ArgCompat {
    private Map<String,String> baseArgs;

    public ArgCompat() {}

    public ArgCompat(Map<String, String> baseArgs) {this.baseArgs = baseArgs;}

    public void setBaseArgs(Map<String, String> baseArgus) {
        this.baseArgs = baseArgs;
    }

    /**
     * 绑定页面时调用
     * @param className 页面类
     * @param intent intent
     */
    public void onAttached(String className,Intent intent){
        if (TextUtils.isEmpty(className)){
            className = "";
        }
        if (baseArgs != null) {
            baseArgs = new HashMap<>();
        }
        baseArgs.put("attached_page",className);
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null){
                Object scheme = extras.get("scheme");
                if (scheme != null){
                    baseArgs.put("attached_scheme",scheme.toString());
                }
            }
        }
    }

    /**
     * 包装最终json
     * @return
     */
    public Map<String,String> convert(String eventId,String eventClassName,Object object){
        Map<String,String> params = new HashMap<>();
        if (baseArgs != null){
            params.putAll(baseArgs);
        }

        Object o = JSONObject.toJSON(object);
        if (o != null){
            if (o instanceof JSONObject){
                JSONObject o1 = (JSONObject) o;
                o1.put("event_class_name",eventClassName);
                o1.put("event_id",eventId);
                TrackerLogger.getLogger().i("ArgCompat  JSONObject",o);

                params.put("data",o1.toJSONString());

            }else if (o instanceof JSONArray){
                TrackerLogger.getLogger().i("ArgCompat  JSONArray 目前不想支持直接传List",o);
            }
        }
        TrackerLogger.getLogger().i("ArgCompat  params",params);
        return params;

    }
}
