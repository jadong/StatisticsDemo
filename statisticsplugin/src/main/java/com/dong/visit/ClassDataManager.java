package com.dong.visit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dong on 2017/9/18.
 */
public class ClassAnnotationManager {

    private static Map<String,ParamsEntity> map = new HashMap<>();

    public static void put(String classFullName,ParamsEntity paramsEntity){
        map.put(classFullName,paramsEntity);
    }

    public static ParamsEntity get(String classFullName){
        return map.get(classFullName);
    }
}
