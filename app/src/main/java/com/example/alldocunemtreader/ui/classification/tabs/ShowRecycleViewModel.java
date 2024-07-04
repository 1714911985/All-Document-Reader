package com.example.alldocunemtreader.ui.classification.tabs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.data.repository.ShowRecycleRepository;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 16:17.
 * Description: com.example.alldocunemtreader.ui.classification.tabs.ShowRecycleViewModel
 */
public class ShowRecycleViewModel extends AndroidViewModel {
    private final ShowRecycleRepository showRecycleRepository;
    private final DocumentInfoRepository documentInfoRepository;

    public ShowRecycleViewModel(@NonNull Application application, ShowRecycleRepository showRecycleRepository, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.showRecycleRepository = showRecycleRepository;
        this.documentInfoRepository = documentInfoRepository;
    }


    public void searchDocumentInfoList(String type, List<Integer> message) {
        showRecycleRepository.searchDocumentInfoList(type, message,documentInfoRepository.getCurrentCache());
    }

    public LiveData<List<DocumentInfo>> getDocumentInfoListLiveData() {
        return showRecycleRepository.getDocumentInfoListLiveData();
    }
}
