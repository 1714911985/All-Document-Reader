package com.example.alldocunemtreader.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Author: Eccentric
 * Created on 2024/6/26 10:35.
 * Description: com.example.alldocunemtreader.utils.FileShareHelper
 */
public class FileShareHelper {
    private static final String TITLE = "Share File";
    public static void shareFile(Context context, String filePath, String mimeType) {
        File file = new File(filePath);
        Uri fileUri = getFileUri(context, file);
        if (fileUri == null) return;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(mimeType);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(Intent.createChooser(shareIntent, TITLE));
    }

    private static Uri getFileUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 使用 FileProvider 来获取文件 URI
            return FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        } else {
            return Uri.fromFile(file);
        }
    }
}

