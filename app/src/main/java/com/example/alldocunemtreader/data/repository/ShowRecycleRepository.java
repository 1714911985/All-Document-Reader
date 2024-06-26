package com.example.alldocunemtreader.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.QueryMethodUtils;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 16:18.
 * Description: com.example.alldocunemtreader.data.repository.ShowRecycleRepository
 */
public class ShowRecycleRepository {
    private Context context;
    private DocumentInfoDao documentInfoDao;
    private MutableLiveData<List<DocumentInfo>> documentInfoListLiveData = new MutableLiveData<>();

    public ShowRecycleRepository(Context context) {
        this.context = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    public void searchDocumentInfoList(String type, List<Integer> message) {
        ThreadPoolManager.getInstance().executeFixed(new Runnable() {
            @Override
            public void run() {
                List<DocumentInfo> documentInfoList =
                        QueryMethodUtils.chooseQueryMethod(documentInfoDao, type, message.get(1), message.get(2));
                documentInfoListLiveData.postValue(documentInfoList);
            }
        });
    }

    public LiveData<List<DocumentInfo>> getDocumentInfoListLiveData() {
        return documentInfoListLiveData;
    }
}
