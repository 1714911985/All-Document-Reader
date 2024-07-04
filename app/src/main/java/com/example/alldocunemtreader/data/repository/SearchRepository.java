package com.example.alldocunemtreader.data.repository;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.data.model.DocumentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 14:36.
 * Description: com.example.alldocunemtreader.data.repository.SearchRepository
 */
public class SearchRepository {
    private final MutableLiveData<List<DocumentInfo>> documentInfoLiveData = new MutableLiveData<>();

    public void searchDocumentByEditText(String text, List<DocumentInfo> allDocumentList) {
        List<DocumentInfo> documentInfoList = new ArrayList<>();
        if (TextUtils.isEmpty(text)) {
            documentInfoList.addAll(allDocumentList);
        } else {
            for (DocumentInfo documentInfo : allDocumentList) {
                if (documentInfo.getName().contains(text)) {
                    documentInfoList.add(documentInfo);
                }
            }
        }
        documentInfoLiveData.postValue(documentInfoList);

    }

    public LiveData<List<DocumentInfo>> getDocumentInfoLiveData() {
        return documentInfoLiveData;
    }
}
