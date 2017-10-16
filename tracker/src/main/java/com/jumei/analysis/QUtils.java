package com.jumei.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kayo on 17/10/12.
 * 快速判断工具类
 */
@SuppressWarnings("WeakerAccess")
public class QUtils {

    public static boolean keysAllInMap(Map<Object, Object> container, Object... keys) {
        if (container == null || keys == null) {
            return false;
        }
        for (Object key : keys) {
            if (!container.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    public static boolean valuesAllInMap(Map<Object, Object> container, Object... values) {
        if (container == null || values == null) {
            return false;
        }
        for (Object value : values) {
            if (!container.containsValue(value)) {
                return false;
            }
        }
        return true;
    }

    public static boolean allIn(List<Object> container, Object... vars) {
        if (container == null) {
            return false;
        }
        for (Object var : vars) {
            if (!container.contains(var)) {
                return false;
            }
        }
        return true;
    }

    public static boolean allIn(String container, String... vars) {
        if (stringIsEmpty(container)) {
            return false;
        }
        if (vars == null) {
            return false;
        }
        for (String var : vars) {
            if (!container.contains(var)) {
                return false;
            }
        }
        return true;
    }

    public static boolean keysOneInMap(Map<Object, Object> container, Object... keys) {
        if (container == null || keys == null) {
            return false;
        }
        for (Object key : keys) {
            if (container.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    public static boolean valuesOneInMap(Map<Object, Object> container, Object... values) {
        if (container == null || values == null) {
            return false;
        }
        for (Object value : values) {
            if (container.containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean oneIn(List<Object> container, Object... vars) {
        if (container == null) {
            return false;
        }
        if (vars == null) {
            return false;
        }
        for (Object var : vars) {
            if (container.contains(var)) {
                return true;
            }
        }
        return false;
    }

    public static boolean oneIn(String container, String... vars) {
        if (stringIsEmpty(container)) {
            return false;
        }
        if (vars == null) {
            return false;
        }
        for (String var : vars) {
            if (container.contains(var)) {
                return true;
            }
        }
        return false;
    }

    public static boolean stringIsEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static boolean stringEquals(String a,String b){
        if (stringIsEmpty(a) || stringIsEmpty(b)){
            return false;
        }
        return a.equals(b);
    }

    public static Map<String,String> mapMoveNull(Map<String,String> map){
        if (map == null){
            return null;
        }
        Map<String,String> temp = new HashMap<>();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key);
            if (value != null
                    && !stringIsEmpty(String.valueOf(value))
                    && !stringEquals(String.valueOf(value),"null")){
                temp.put(key,value);
            }
        }
        return temp;
    }


}
