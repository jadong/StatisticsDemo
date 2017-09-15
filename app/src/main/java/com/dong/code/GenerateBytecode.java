package com.dong.code;

import java.util.Map;

/**
 * Created by dong on 2017/9/14.
 */
public class GenerateBytecode {

    public void code(String methodName, Map<String, Object> params) {
        PrintUtils.printParams(methodName, params);
    }

}
