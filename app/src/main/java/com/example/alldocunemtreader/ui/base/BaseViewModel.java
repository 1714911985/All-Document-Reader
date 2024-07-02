package com.example.alldocunemtreader.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.repository.BaseRepository;
import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;

/**
 * Author: Eccentric
 * Created on 2024/6/26 10:56.
 * Description: com.example.alldocunemtreader.ui.base.BaseViewModel
 */
public class BaseViewModel extends AndroidViewModel {
    private BaseRepository baseRepository;
    private DocumentInfoRepository documentInfoRepository;

    public BaseViewModel(@NonNull Application application, BaseRepository baseRepository, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.baseRepository = baseRepository;
        this.documentInfoRepository =documentInfoRepository;
    }

    public void switchFavoriteStatus(DocumentInfo currentFile) {
        baseRepository.switchFavoriteStatus(currentFile,documentInfoRepository.getCurrentCache());
    }

    public LiveData<Integer> getIsFavoriteLiveData() {
        return baseRepository.getIsFavoriteLiveData();
    }

    public void changeFileName(String newFileName, DocumentInfo currentFile) {
        baseRepository.changeFileName(newFileName, currentFile,documentInfoRepository.getCurrentCache());
    }

    public void deleteFile(DocumentInfo currentFile) {
        baseRepository.deleteFile(currentFile,documentInfoRepository.getCurrentCache());
    }
}
