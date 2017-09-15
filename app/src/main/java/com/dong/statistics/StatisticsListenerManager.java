package com.dong.statistics;

import java.util.ArrayList;
import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/9/5.
 */
public class StatisticsListenerManager {

    private static List<StatisticsListener> listeners = new ArrayList<>();

    public static void register(StatisticsListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public static void unregister(StatisticsListener listener) {
        listeners.remove(listener);
    }

    public interface StatisticsListener {

        void onViewClick();

    }

}
