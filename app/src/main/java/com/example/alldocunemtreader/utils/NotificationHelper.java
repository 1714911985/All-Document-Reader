package com.example.alldocunemtreader.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.ui.main.MainActivity;

/**
 * Author: Eccentric
 * Created on 2024/6/21 18:26.
 * Description: NotificationHelper类 创建通知渠道并发送通知
 */
public class NotificationHelper {
    private static final String CHANNEL_ID = "DefaultImportanceChannel";
    private static final String CHANNEL_NAME = "DefaultImportanceChannel";
    private static final String PERMANENT_NOTIFICATION_TITLE = "All Document Reader";
    private static final Integer PERMANENT_NOTIFICATION_ID = 1;
    private static final Integer SCREENSHOT_NOTIFICATION_ID = 2;

    private final Context context;
    private final NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            // 注册通知渠道
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendPermanentNotification() {
        // 设置点击通知后的操作
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification permanentNotification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_app)
                .setContentTitle(PERMANENT_NOTIFICATION_TITLE)
                .setContentText(context.getResources().getText(R.string.permanent_notification_text))
                .setAutoCancel(false)  // 设置点击后不自动取消
                .setContentIntent(pendingIntent)
                .setOngoing(true)// 设置为持续通知，点击通知不会消失
                .build();

        // 发送通知
        notificationManager.notify(PERMANENT_NOTIFICATION_ID, permanentNotification);
    }

    public void sendScreenShotNotification() {
        // 创建通知
        Notification screenShotNotification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getResources().getText(R.string.screenshot_notification_title))//Screenshot notification
                .setContentText(context.getResources().getText(R.string.screenshot_notification_text))//You have taken a screenshot
                .setSmallIcon(R.drawable.logo_app)
                .setAutoCancel(true)
                .build();

        // 发送通知
        notificationManager.notify(SCREENSHOT_NOTIFICATION_ID, screenShotNotification);
    }

}
