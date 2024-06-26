package com.example.alldocunemtreader.utils;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.MMKVKeyConstants;

/**
 * Author: Eccentric
 * Created on 2024/6/24 15:54.
 * Description: com.example.alldocunemtreader.utils.ThemeModeManager
 */
public class ThemeModeManager {
    public static void initThemeMode(){
        int mode = MMKVManager.decodeInt(MMKVKeyConstants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                setLightMode();
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                setNightMode();
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                setAutoMode();
                break;
            default:
                break;
        }
    }
    public static void listenThemeModeChanged(int checkedId) {
        if (checkedId == R.id.rb_auto_mode) {//自动模式
            setAutoMode();
        } else if (checkedId == R.id.rb_light_mode) {//日间模式
            setLightMode();
        } else if (checkedId == R.id.rb_dark_mode) {//夜间模式
            setNightMode();
        }
    }

    // 日间模式
    private static void setLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        saveThemeMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    // 夜间模式
    private static void setNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        saveThemeMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    // 自动模式
    private static void setAutoMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        saveThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private static void saveThemeMode(int mode) {
        MMKVManager.encode(MMKVKeyConstants.MODE, mode);
    }
}
