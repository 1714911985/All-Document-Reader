package com.example.alldocunemtreader.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.DocumentRelatedConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Author: Eccentric
 * Created on 2024/6/24 10:29.
 * Description: com.example.alldocunemtreader.utils.DocumentUtils
 */
public class DocumentUtils {
    /**
     * 根据文件名获取文件类型
     */
    public static String getFileType(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {

            String end = fileName.substring(index + 1).toLowerCase();
            switch (end) {
                case DocumentRelatedConstants.SUFFIX_PPT:
                case DocumentRelatedConstants.SUFFIX_PPTX:
                    return DocumentRelatedConstants.TYPE_PPT;
                case DocumentRelatedConstants.SUFFIX_DOC:
                case DocumentRelatedConstants.SUFFIX_DOCX:
                    return DocumentRelatedConstants.TYPE_DOC;
                case DocumentRelatedConstants.SUFFIX_XLS:
                case DocumentRelatedConstants.SUFFIX_XLSX:
                    return DocumentRelatedConstants.TYPE_XLS;
                case DocumentRelatedConstants.SUFFIX_PDF:
                    return DocumentRelatedConstants.TYPE_PDF;
                case DocumentRelatedConstants.SUFFIX_TXT:
                    return DocumentRelatedConstants.TYPE_TXT;
                case DocumentRelatedConstants.SUFFIX_JSON:
                case DocumentRelatedConstants.SUFFIX_XML:
                    return DocumentRelatedConstants.TYPE_OTHER;
            }
        }
        return DocumentRelatedConstants.TYPE_UNKNOWN;
    }

    /**
     * 根据文件类型返回IconId
     *
     * @param fileType
     * @return
     */
    public static int getIconResourceForFileType(String fileType) {
        switch (fileType) {
            case DocumentRelatedConstants.TYPE_PPT:
                return R.drawable.ic_item_file_ppt;
            case DocumentRelatedConstants.TYPE_DOC:
                return R.drawable.ic_item_file_doc;
            case DocumentRelatedConstants.TYPE_XLS:
                return R.drawable.ic_item_file_xls;
            case DocumentRelatedConstants.TYPE_PDF:
                return R.drawable.ic_item_file_pdf;
            case DocumentRelatedConstants.TYPE_TXT:
                return R.drawable.ic_item_file_txt;
            case DocumentRelatedConstants.TYPE_OTHER:
                return R.drawable.ic_item_file_other;
        }
        return 0;
    }

    /**
     * 根据 创建时间 和 文件大小  返回格式化好的字符串
     *
     * @param createdTime
     * @param fileSize
     * @return
     */
    public static CharSequence getFileTimeAndSize(long createdTime, long fileSize) {
        return formatTime(createdTime) + " | " + formatSize(fileSize);
    }

    /**
     * 根据时间返回格式化字符串
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // 获取 Date 对象
        Date date = new Date(time);

        // 格式化日期
        String formattedDate = sdfDate.format(date);

        // 获取小时和分钟
        String formattedTime = sdfTime.format(date);
        String[] timeParts = formattedTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        String minutes = timeParts[1];

        // 处理 AM/PM
        String period;
        if (hour == 0) {
            hour = 12;
            period = "AM";
        } else if (hour == 12) {
            period = "PM";
        } else if (hour > 12) {
            hour -= 12;
            period = "PM";
        } else {
            period = "AM";
        }

        // 格式化小时
        String formattedHour = String.format(Locale.getDefault(), "%02d", hour);
        return formattedDate + " " + formattedHour + ":" + minutes + " " + period;
    }

    /**
     * 根据大小返回格式化好的字符串
     *
     * @param fileSize
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String formatSize(long fileSize) {
        if (fileSize < 1024) {
            return fileSize + ".0 B";
        }
        int exp = (int) (Math.log(fileSize) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", fileSize / Math.pow(1024, exp), unit);
    }

    /**
     * 根据完整文件名获取不带后缀的文件名
     *
     * @param fileName
     * @return
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * 根据完整文件名获取后缀   .xxx
     *
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf(".") > 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    /**
     * 把路径 转换为 Uri
     *
     * @param context
     * @param filePath
     * @return
     */
    public static Uri changePathToUri(Context context, String filePath) {
        String authority = "com.example.allreader.fileprovider";
        File file = new File(filePath);
        return FileProvider.getUriForFile(context, authority, file);
    }

    /**
     * 根据文件类型获取对应的背景颜色
     *
     * @param fileType
     * @return
     */
    public static int getBackgroundColorForFileType(String fileType) {
        switch (fileType) {
            case DocumentRelatedConstants.TYPE_PPT:
                return R.color.ppt_background_color;
            case DocumentRelatedConstants.TYPE_DOC:
                return R.color.doc_background_color;
            case DocumentRelatedConstants.TYPE_XLS:
                return R.color.xls_background_color;
            case DocumentRelatedConstants.TYPE_PDF:
                return R.color.pdf_background_color;
            case DocumentRelatedConstants.TYPE_TXT:
                return R.color.txt_background_color;
            case DocumentRelatedConstants.TYPE_OTHER:
                return R.color.other_background_color;
        }
        return 0;
    }

    /**
     * 根据文件路径获取对应的 MimeType
     *
     * @param path
     * @return
     */
    public static String getMimeTypeByType(String path) {
        int index = path.lastIndexOf('.');
        String suffix = path.substring(index + 1).toLowerCase();
        switch (suffix) {
            case DocumentRelatedConstants.SUFFIX_DOC:
                return DocumentRelatedConstants.MIME_TYPE_DOC;
            case DocumentRelatedConstants.SUFFIX_DOCX:
                return DocumentRelatedConstants.MIME_TYPE_DOCX;
            case DocumentRelatedConstants.SUFFIX_PDF:
                return DocumentRelatedConstants.MIME_TYPE_PDF;
            case DocumentRelatedConstants.SUFFIX_PPTX:
                return DocumentRelatedConstants.MIME_TYPE_PPTX;
            case DocumentRelatedConstants.SUFFIX_PPT:
                return DocumentRelatedConstants.MIME_TYPE_PPT;
            case DocumentRelatedConstants.SUFFIX_XLS:
                return DocumentRelatedConstants.MIME_TYPE_XLS;
            case DocumentRelatedConstants.SUFFIX_XLSX:
                return DocumentRelatedConstants.MIME_TYPE_XLSX;
            case DocumentRelatedConstants.SUFFIX_TXT:
                return DocumentRelatedConstants.MIME_TYPE_TXT;
            case DocumentRelatedConstants.SUFFIX_XML:
                return DocumentRelatedConstants.MIME_TYPE_XML;
            case DocumentRelatedConstants.SUFFIX_JSON:
                return DocumentRelatedConstants.MIME_TYPE_JSON;
            default:
                return null;
        }
    }

    public static String getParentPath(String path) {
        File file = new File(path);
        String parentPath = file.getParent();
        if (parentPath != null) {
            // 确保父路径以斜杠结尾
            if (!parentPath.endsWith(File.separator)) {
                parentPath += File.separator;
            }
        }
        return parentPath;
    }

    /**
     * 从uri中获取文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFileNameFromUri(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String filePath = null;

        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                filePath = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String filePath = null;
        if (Objects.equals(uri.getScheme(), "content")) {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        String fileName = cursor.getString(index);
                        File cacheDir = context.getCacheDir();
                        File file = new File(cacheDir, fileName);
                        try (InputStream inputStream = contentResolver.openInputStream(uri);
                             OutputStream outputStream = new FileOutputStream(file)) {
                            byte[] buffer = new byte[4 * 1024];
                            int read;
                            while ((read = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, read);
                            }
                            outputStream.flush();
                        }
                        filePath = file.getAbsolutePath();
                    }
                } catch (IOException e) {
                    Log.e("FileUtils", "Error copying file", e);
                } finally {
                    cursor.close();
                }
            }
        }
        return filePath;
    }

}
