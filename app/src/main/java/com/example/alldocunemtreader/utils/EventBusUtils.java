package com.example.alldocunemtreader.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Author: Eccentric
 * Created on 2024/6/24 11:49.
 * Description: com.example.alldocunemtreader.utils.EventBusUtils
 */
public class EventBusUtils {
    public static void register(Object o) {
        if (!EventBus.getDefault().isRegistered(o)) {
            EventBus.getDefault().register(o);
        }
    }

    public static void post(Object o) {
        EventBus.getDefault().post(o);
    }

    public static void unregister(Object o) {
        if (EventBus.getDefault().isRegistered(o)) {
            EventBus.getDefault().unregister(o);
        }
    }
}
