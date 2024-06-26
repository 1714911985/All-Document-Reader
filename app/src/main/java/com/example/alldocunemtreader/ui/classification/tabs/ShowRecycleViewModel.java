package com.example.alldocunemtreader.ui.classification.tabs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.repository.ShowRecycleRepository;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 16:17.
 * Description: com.example.alldocunemtreader.ui.classification.tabs.ShowRecycleViewModel
 */
public class ShowRecycleViewModel extends AndroidViewModel {
    private ShowRecycleRepository showRecycleRepository;

    public ShowRecycleViewModel(@NonNull Application application, ShowRecycleRepository showRecycleRepository) {
        super(application);
        this.showRecycleRepository = showRecycleRepository;
    }


    public void searchDocumentInfoList(String type, List<Integer> message) {
        showRecycleRepository.searchDocumentInfoList(type, message);
    }

    public LiveData<List<DocumentInfo>> getDocumentInfoListLiveData() {
        return showRecycleRepository.getDocumentInfoListLiveData();
    }
}
