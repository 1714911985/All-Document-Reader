package com.example.alldocunemtreader.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/7/1 13:35.
 * Description: com.example.alldocunemtreader.data.repository.DocumentInfoRepository
 */
public class DocumentInfoRepository {
    private Context context;
    private MutableLiveData<List<DocumentInfo>> cacheLiveData;
    private DocumentInfoDao documentInfoDao;
    private static DocumentInfoRepository instance;

    private DocumentInfoRepository(Context context) {
        this.context = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    public static DocumentInfoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DocumentInfoRepository(context);
        }
        return instance;
    }

    public LiveData<List<DocumentInfo>> getCacheLiveData(){
        return cacheLiveData;
    }

    public List<DocumentInfo> getCurrentCache(){
        return cacheLiveData.getValue();
    }

    public void fetchCache(){
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                List<DocumentInfo> allDocuments = documentInfoDao.getAllDocuments();
                cacheLiveData.postValue(allDocuments);
            }
        });
    }


}
