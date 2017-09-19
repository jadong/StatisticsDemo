package com.dong.code;

import com.jumei.analysis.Tracker;

/**
 * Created by dong on 2017/9/14.
 */
public class GenerateBytecode {

    private Object object;

    public void code() {

        String eventId = "EEEE";
        String className = "CCCC";
        Tracker.onClick(eventId, className, GenerateBytecode.this.object);

    }

}
