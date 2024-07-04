package com.example.alldocunemtreader.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/7/1 13:35.
 * Description: com.example.alldocunemtreader.data.repository.DocumentInfoRepository
 */
public class DocumentInfoRepository {
    private final MutableLiveData<List<DocumentInfo>> cacheLiveData = new MutableLiveData<>();
    private final DocumentInfoDao documentInfoDao;
    private static DocumentInfoRepository instance;

    private DocumentInfoRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
        cacheLiveData.setValue(new ArrayList<>());
    }

    public static DocumentInfoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DocumentInfoRepository(context);
        }
        return instance;
    }

    public LiveData<List<DocumentInfo>> getCacheLiveData() {
        return cacheLiveData;
    }

    public List<DocumentInfo> getCurrentCache() {
        return cacheLiveData.getValue();
    }

    public void fetchCache() {
        List<DocumentInfo> allDocuments = documentInfoDao.getAllDocuments();
        cacheLiveData.postValue(allDocuments);
        EventBusUtils
                .post(new EventBusMessage<>(RequestCodeConstants.REQUEST_SCAN_FINISHED, null));

    }

}
