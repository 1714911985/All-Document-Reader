package com.example.alldocunemtreader.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.data.repository.ScanRepository;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

/**
 * Author: Eccentric
 * Created on 2024/6/21 18:12.
 * Description: com.example.alldocunemtreader.ui.main.MainViewModel
 */
public class MainViewModel extends AndroidViewModel {
    private final ScanRepository scanRepository;
    private final DocumentInfoRepository documentInfoRepository;

    public MainViewModel(@NonNull Application application, ScanRepository scanRepository, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.scanRepository = scanRepository;
        this.documentInfoRepository = documentInfoRepository;
    }

    public void startScan(){
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                scanRepository.startScan();
                documentInfoRepository.fetchCache();
            }
        });
    }
}
