package com.jumei.analysis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by kayo on 17/9/18.
 * 参数包装类
 */
public class ArgCompat {
    private Map<String,String> baseArgs;
    private Map<String,String> pageArgs;
    private Map<String,String> schemeArgs;

    public ArgCompat() {}

    public ArgCompat(Map<String, String> baseArgs) {
        setBaseArgs(baseArgs);
    }

    public void setBaseArgs(Map<String, String> baseArgs) {
        this.baseArgs = baseArgs;
        if (this.baseArgs == null){
            this.baseArgs = new HashMap<>();
        }
        this.baseArgs.put(Content.OS_VERSION,Config.OS_VERSION);
        this.baseArgs.put(Content.MOBILE_BRAND,Config.MOBILE_BRAND);
        this.baseArgs.put(Content.MOBILE_TYPE,Config.MOBILE_TYPE);
        this.baseArgs.put(Content.MOBILE_IMEI,Config.MOBILE_IMEI);
        this.baseArgs.put(Content.MOBILE_IMSI,Config.MOBILE_IMSI);
        this.baseArgs.put(Content.MOBILE_ISROOT,Config.MOBILE_ISROOT);
        this.baseArgs.put(Content.MOBILE_NUMBER,Config.MOBILE_NUMBER);
        this.baseArgs.put(Content.OS,"Android");
    }

    public void setPageArgs(Map<String, String> pageArgs){
        this.pageArgs = pageArgs;
    }

    /**
     * 绑定页面时调用
     */
    public void onAttached(Context context){
        if (context == null) {
            return;
        }
        String simpleName = context.getClass().getSimpleName();
        if (pageArgs == null) {
            pageArgs = new HashMap<>();
        }
        pageArgs.put(Content.ATTACHED_PAGE,simpleName);
        if (context instanceof Activity){
            Intent intent = ((Activity) context).getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null){
                    Set<String> keys = extras.keySet();
                    for (String key : keys) {
                        Object value = extras.get(key);
                        if (QUtils.stringEquals(key,"scheme")){
                            if (value != null){
                                setSchemeArgs(String.valueOf(value));
                            }
                        }else {
                            pageArgs.put(key,String.valueOf(value));
                        }
                    }
                }
            }
        }
    }

    /**
     * 包装最终json
     * @return
     */
    public Map<String,String> convert(String eventId,String eventClassName,
                                      Map<String,String> tagParams,Object object){
        Map<String,String> params = new HashMap<>();
        if (baseArgs != null){
            params.putAll(baseArgs);
        }
        if (tagParams != null){
            params.putAll(tagParams);
        }
        if (pageArgs != null){
            params.putAll(pageArgs);
        }
        if (schemeArgs != null){
            params.putAll(schemeArgs);
        }
        params.put(Content.EVENT_CLASS_NAME,eventClassName);
        if (!QUtils.stringIsEmpty(eventId)){
            params.put(Content.EVENT_ID,eventId);
        }

        Object o = JSONObject.toJSON(object);
        if (o != null){
            if (o instanceof JSONObject){
                JSONObject o1 = (JSONObject) o;
                //解析 json中的所有 基础数据的key
                Map<String, String> map = parseJsonObject(o1);
                params.putAll(map);
//                params.put(Content.EVENT_DATA,o1.toJSONString());
            }else if (o instanceof JSONArray){
                TrackerLogger.getLogger().i("ArgCompat  JSONArray 目前不想支持直接传List",o);
            }
        }
        return params;
    }

    //解析json
    private Map<String,String> parseJsonObject(JSONObject jsonObject){
        Map<String,String> params = new HashMap<>();
        if (jsonObject == null){
            return params;
        }
        Set<String> jsonKeys = jsonObject.keySet();
        for (String jsonKey : jsonKeys) {
            Object value = jsonObject.get(jsonKey);
            if ( value instanceof JSONObject){
                params.putAll(parseJsonObject((JSONObject) value));
            }else {
                params.put(jsonKey,String.valueOf(value));
            }
        }
        return params;

    }

    private void setSchemeArgs(String scheme){
        if (!TextUtils.isEmpty(scheme)){
            schemeArgs = new HashMap<>();
            String baseScheme = scheme;
            if (scheme.contains("?")){
                String[] split = scheme.split("\\?");
                baseScheme = split[0];
                String params = split[1];
                if (params.contains("=")){
                    if (params.contains("&")){
                        System.out.println("多参数截取");
                        //多参数
                        String[] paramsTemp = params.split("&");
                        for (String param : paramsTemp) {
                            if (param.contains("=")){
                                String[] paramTemp = param.split("=");
                                if (paramTemp.length == 2) {
                                    try {
                                        schemeArgs.put(paramTemp[0], URLDecoder.decode(paramTemp[1],"UTF-8"));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }else {
                        System.out.println("单参数截取");
                        //单参数
                        String[] param = params.split("=");
                        if (param.length==2) {
                            try {
                                schemeArgs.put(param[0],URLDecoder.decode(param[1],"UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            schemeArgs.put(Content.ATTACHED_SCHEME,baseScheme);
        }
    }
}
