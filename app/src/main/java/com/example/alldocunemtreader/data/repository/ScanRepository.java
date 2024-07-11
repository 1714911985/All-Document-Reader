package com.example.alldocunemtreader.data.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.constants.MMKVKeyConstants;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.DocumentUtils;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.MMKVManager;
import com.example.alldocunemtreader.utils.ThreadPoolManager;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.util.Objects;

/**
 * Author: Eccentric
 * Created on 2024/6/24 10:04.
 * Description: com.example.alldocunemtreader.data.repository.ScanRepository
 */
public class ScanRepository {
    private static final String EXTERNAL = "external";
    private final Context context;
    private final DocumentInfoDao documentInfoDao;
    private int isDelete;//判断哪些数据是在两次扫描之间已经被删除过的

    public ScanRepository(Context context) {
        this.context = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    public void startScan() {
        Trace trace = FirebasePerformance.getInstance().newTrace("文件扫描");
        trace.start();
        ContentResolver resolver = context.getContentResolver();
        //Table
        Uri table = MediaStore.Files.getContentUri(EXTERNAL);
        //Column
        String[] column = {MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.SIZE};
        //Where
        String where = assembleWhere();
        //args
        String[] args = getMimeTypeFromExtension();

        Cursor cursor = resolver.query(table, column, where, args,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC");
        if (cursor != null) {
            int count = cursor.getCount();
            if (count > 0) {
                //扫描有结果
                int columnIndexOfData = cursor.
                        getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                int columnIndexOfDateModified = cursor.
                        getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED);
                int columnIndexOfDateAdded = cursor.
                        getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED);
                int columnIndexOfSize = cursor.
                        getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);

                isDelete = MMKVManager.decodeInt(MMKVKeyConstants.IS_DELETE, 0);
                while (cursor.moveToNext()) {
                    String pathStr = cursor.getString(columnIndexOfData);
                    long dateModifiedLong = cursor.getLong(columnIndexOfDateModified);
                    long dateAddedLong = cursor.getLong(columnIndexOfDateAdded);
                    long sizeLong = cursor.getLong(columnIndexOfSize);

                    int index = pathStr.lastIndexOf('/');
                    String fileName = pathStr.substring(index + 1).toLowerCase();
                    String fileType = DocumentUtils.getFileType(fileName);
                    DocumentInfo queryDocumentByPath = documentInfoDao.queryDocumentByPath(pathStr);

                    if (!Objects.isNull(queryDocumentByPath)) {
                        documentInfoDao.update(fileName, pathStr, sizeLong, fileType,
                                dateAddedLong, dateModifiedLong, 1 - isDelete);
                    } else {
                        DocumentInfo files = new DocumentInfo(fileName, pathStr, sizeLong, fileType,
                                dateAddedLong, dateModifiedLong, 0L, 0, 1 - isDelete);

                        documentInfoDao.insert(files);
                    }
                }
            }
            cursor.close();
        }
        documentInfoDao.delete(isDelete);
        MMKVManager.encode(MMKVKeyConstants.IS_DELETE, 1 - isDelete);
        trace.stop();
    }

    private String[] getMimeTypeFromExtension() {
        String doc = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_DOC);
        String docx = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_DOCX);
        String ppt = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_PPT);
        String pptx = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_PPTX);
        String xls = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_XLS);
        String xlsx = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_XLSX);
        String pdf = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_PDF);
        String txt = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_TXT);
        String xml = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_XML);
        String json = MimeTypeMap.getSingleton().
                getMimeTypeFromExtension(DocumentRelatedConstants.SUFFIX_JSON);
        return new String[]{doc, docx, pptx, ppt, xls, xlsx, pdf, txt, xml, json};
    }

    private String assembleWhere() {
        return "(" + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?)"
                + " AND " + MediaStore.Files.FileColumns.SIZE + " > 0";
    }


}
