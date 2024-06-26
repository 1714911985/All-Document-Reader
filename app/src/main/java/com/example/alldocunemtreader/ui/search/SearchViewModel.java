package com.example.alldocunemtreader.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.repository.SearchRepository;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 14:30.
 * Description: com.example.alldocunemtreader.ui.search.SearchViewModel
 */
public class SearchViewModel extends AndroidViewModel {
    private SearchRepository searchRepository;

    public SearchViewModel(@NonNull Application application, SearchRepository searchRepository) {
        super(application);
        this.searchRepository = searchRepository;
    }

    public void searchDocumentByEditText(String text) {
        searchRepository.searchDocumentByEditText(text);
    }

    public LiveData<List<DocumentInfo>> getDocumentInfoLiveData(){
        return searchRepository.getDocumentInfoLiveData();
    }
}
