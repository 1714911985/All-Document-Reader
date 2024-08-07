package com.example.alldocunemtreader.utils;

import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * Author: Eccentric
 * Created on 2024/6/14 14:26.
 * Description: com.example.allreader.utils.Manager.MMKVManager
 */
public class MMKVManager {
    private static MMKV mmkv;

    public static void initialize(Context context) {
        MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
    }

    public static void encode(String key, int value) {
        mmkv.encode(key, value);
    }

    public static int decodeInt(String key, int defaultValue) {
        return mmkv.decodeInt(key, defaultValue);
    }

    public static void encode(String key, String value) {
        mmkv.encode(key, value);
    }

    public static String decodeString(String key, String defaultValue) {
        return mmkv.decodeString(key, defaultValue);
    }

    public static boolean encode(String key, boolean value) {
        return mmkv.encode(key, value);
    }

    public static boolean decodeBool(String key, boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }

}