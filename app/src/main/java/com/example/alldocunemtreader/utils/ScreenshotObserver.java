package com.example.alldocunemtreader.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

/**
 * Author: Eccentric
 * Created on 2024/6/21 18:57.
 * Description: 截屏监听器
 */
public class ScreenshotObserver extends ContentObserver {
    private final Context context;

    public ScreenshotObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        if (uri != null && uri.toString().contains(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())) {
            String[] projection = {MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                if (path.toLowerCase().contains("screenshots")) {
                    new NotificationHelper(context).sendScreenShotNotification();
                }
                cursor.close();
            }
        }
    }

    // 在合适的地方注册截屏监听器
    public static void registerScreenshotObserver(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        ScreenshotObserver observer = new ScreenshotObserver(context, new Handler(Looper.getMainLooper()));
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true, observer);
    }

    // 在适当的地方取消注册截屏监听器
    public static void unregisterScreenshotObserver(Context context, ScreenshotObserver observer) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.unregisterContentObserver(observer);
    }
}