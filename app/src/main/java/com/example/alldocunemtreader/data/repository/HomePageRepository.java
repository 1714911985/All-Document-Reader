package com.example.alldocunemtreader.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.data.model.DocumentCounts;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

/**
 * Author: Eccentric
 * Created on 2024/6/24 14:17.
 * Description: com.example.alldocunemtreader.data.repository.HomePageRepository
 */
public class HomePageRepository {
    private DocumentInfoDao documentInfoDao;
    private MutableLiveData<DocumentCounts> documentCountsLiveData = new MutableLiveData<>();
    private DocumentCounts documentCounts;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    fetchDocumentCounts();
                    break;
                default:
                    break;
            }
        }
    };

    public LiveData<DocumentCounts> getDocumentCountsLiveData() {
        return documentCountsLiveData;
    }

    public HomePageRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    public void fetchDocumentCounts() {
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                int allDocumentCount = documentInfoDao.getAllDocumentCount();
                int allPDFCount = documentInfoDao.getAllPDFCount();
                int allDOCCount = documentInfoDao.getAllDOCCount();
                int allXLSCount = documentInfoDao.getAllXLSCount();
                int allPPTCount = documentInfoDao.getAllPPTCount();
                int allTXTCount = documentInfoDao.getAllTXTCount();
                int allOTHERCount = documentInfoDao.getAllOTHERCount();

                documentCounts = new DocumentCounts(allDocumentCount, allPDFCount, allDOCCount,
                        allXLSCount, allPPTCount, allTXTCount, allOTHERCount);
                documentCountsLiveData.postValue(documentCounts);
            }
        });
    }
}
