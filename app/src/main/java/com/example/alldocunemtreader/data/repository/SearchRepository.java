package com.example.alldocunemtreader.data.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 14:36.
 * Description: com.example.alldocunemtreader.data.repository.SearchRepository
 */
public class SearchRepository {
    private Context context;
    private DocumentInfoDao documentInfoDao;
    private MutableLiveData<List<DocumentInfo>> documentInfoLiveData = new MutableLiveData<>();

    public SearchRepository(Context context) {
        this.context = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    public void searchDocumentByEditText(String text) {
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                List<DocumentInfo> documentInfoList = null;

                if (TextUtils.isEmpty(text)){
                   // documentInfoList = documentInfoDao.getAllDocuments();
                }else {
                    documentInfoList = documentInfoDao.fuzzyGetDocuments(text);
                }
                documentInfoLiveData.postValue(documentInfoList);
            }
        });
    }

    public LiveData<List<DocumentInfo>> getDocumentInfoLiveData(){
        return documentInfoLiveData;
    }
}
