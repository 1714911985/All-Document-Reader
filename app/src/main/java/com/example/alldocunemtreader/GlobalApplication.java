package com.example.alldocunemtreader;

import android.app.Application;
import android.content.Context;

import com.example.alldocunemtreader.utils.MMKVManager;
import com.example.alldocunemtreader.utils.NotificationHelper;
import com.google.firebase.perf.FirebasePerformance;

/**
 * Author: Eccentric
 * Created on 2024/6/21 17:09.
 * Description: 全局配置Application
 */
public class GlobalApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        MMKVManager.initialize(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new NotificationHelper(this).createNotificationChannel();
        FirebasePerformance.getInstance().setPerformanceCollectionEnabled(true);


    }
}


