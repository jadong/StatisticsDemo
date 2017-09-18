package com.dong.visit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dong on 2017/9/18.
 */
public class ClassDataManager {

    private static Map<String,FieldEntity> fieldDataMap = new HashMap<>();
    private static Map<String,MethodEntity> methodDataMap = new HashMap<>();

    public static void putFieldData(String classFullName, FieldEntity fieldEntity){
        fieldDataMap.put(classFullName, fieldEntity);
    }

    public static FieldEntity getFieldData(String classFullName){
        return fieldDataMap.get(classFullName);
    }

    public static void putMethodData(String classFullName, MethodEntity methodEntity){
        methodDataMap.put(classFullName, methodEntity);
    }

    public static MethodEntity getMethodData(String classFullName){
        return methodDataMap.get(classFullName);
    }
}
