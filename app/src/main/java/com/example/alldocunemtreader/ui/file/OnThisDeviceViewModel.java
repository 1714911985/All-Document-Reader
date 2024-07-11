package com.example.alldocunemtreader.ui.file;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.alldocunemtreader.data.model.FileItem;
import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/7/10 17:09.
 * Description: com.example.alldocunemtreader.ui.file.OnThisDeviceViewModel
 */
public class OnThisDeviceViewModel extends AndroidViewModel {
    private final DocumentInfoRepository documentInfoRepository;

    public OnThisDeviceViewModel(@NonNull Application application, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.documentInfoRepository = documentInfoRepository;
    }

    public List<FileItem> getFileItemList() {
        return documentInfoRepository.getFileItemList();
    }
}
