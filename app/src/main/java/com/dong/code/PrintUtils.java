package com.dong.code;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by dong on 2017/9/14.
 */
public class PrintUtils {

    public static void printParams(String methodName, Map<String, Object> params) {
        if (params == null) {
            return;
        }
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            stringBuilder.append("name:")
                    .append(entry.getKey())
                    .append(",value:")
                    .append(entry.getValue())
                    .append("\n");
        }
        System.out.println("print-params [" + methodName + "]:\n" + stringBuilder.toString());
    }

    public static void main (String[] args) throws java.lang.Exception {
        System.out.println(String.class.getCanonicalName());
        System.out.println(String.class.getName());

    }

}