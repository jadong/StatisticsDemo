package com.jumei.analysis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

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
    }

    /**
     * 绑定页面时调用
     */
    public void onAttached(Context context){
        if (context == null) {
            return;
        }
        String simpleName = context.getClass().getSimpleName();
        if (baseArgs == null) {
            baseArgs = new HashMap<>();
        }
        baseArgs.put(Content.ATTACHED_PAGE,simpleName);
        if (context instanceof Activity){
            Intent intent = ((Activity) context).getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null){
                    Object scheme = extras.get("scheme");
                    if (scheme != null && scheme instanceof String){
                        setSchemeArgs(String.valueOf(scheme));
                    }
                    //TODO 根据配置文件读取页面数据

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
                TrackerLogger.getLogger().i("convert","BeanToJson",o1);
                o1.put(Content.EVENT_CLASS_NAME,eventClassName);
                o1.put(Content.EVENT_ID,eventId);

                if (schemeArgs !=null && !schemeArgs.isEmpty()){
                    Set<String> keys = schemeArgs.keySet();
                    for (String key : keys) {
                        String value = schemeArgs.get(key);
                        if (Content.ATTACHED_SCHEME.equals(key)){
                            ///放入params
                            params.put(Content.ATTACHED_SCHEME,value);
                        }else {
                            ///放入object
                            o1.put(key,value);
                        }
                    }
                }
                params.put(Content.EVENT_DATA,o1.toJSONString());
                TrackerLogger.getLogger().i("ArgCompat  JSONObject",o);
            }else if (o instanceof JSONArray){
                TrackerLogger.getLogger().i("ArgCompat  JSONArray 目前不想支持直接传List",o);
            }
        }
        TrackerLogger.getLogger().i("ArgCompat  params",params);
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
