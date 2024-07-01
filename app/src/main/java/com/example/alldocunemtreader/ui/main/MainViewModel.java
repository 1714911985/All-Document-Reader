package com.example.alldocunemtreader.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.data.repository.ScanRepository;

/**
 * Author: Eccentric
 * Created on 2024/6/21 18:12.
 * Description: com.example.alldocunemtreader.ui.main.MainViewModel
 */
public class MainViewModel extends AndroidViewModel {
    private final ScanRepository scanRepository;

    public MainViewModel(@NonNull Application application, ScanRepository scanRepository, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.scanRepository = scanRepository;
    }

    public void startScan(){
        scanRepository.startScan();
    }
}
