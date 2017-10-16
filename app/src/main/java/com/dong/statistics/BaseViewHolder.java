package com.dong.statistics;

import android.content.Context;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dong on 2017/9/20.
 */
public abstract class BaseViewHolder {

    private Context context;
    private View itemView;
    private Map<String,String> params = new HashMap<>();
    private Map<String,String> viewParams = new HashMap<>();

    public BaseViewHolder(Context context) {
        this.context = context;
        itemView = new View(context);

    }
}
