package com.example.alldocunemtreader.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.repository.BaseRepository;

/**
 * Author: Eccentric
 * Created on 2024/6/26 10:56.
 * Description: com.example.alldocunemtreader.ui.base.BaseViewModel
 */
public class BaseViewModel extends AndroidViewModel {
    private BaseRepository baseRepository;

    public BaseViewModel(@NonNull Application application, BaseRepository baseRepository) {
        super(application);
        this.baseRepository = baseRepository;
    }

    public void switchFavoriteStatus(DocumentInfo currentFile) {
        baseRepository.switchFavoriteStatus(currentFile);
    }

    public LiveData<Integer> getIsFavoriteLiveData() {
        return baseRepository.getIsFavoriteLiveData();
    }

    public void changeFileName(String newFileName, DocumentInfo currentFile) {
        baseRepository.changeFileName(newFileName, currentFile);
    }

    public void deleteFile(DocumentInfo currentFile) {
        baseRepository.deleteFile(currentFile);
    }
}
