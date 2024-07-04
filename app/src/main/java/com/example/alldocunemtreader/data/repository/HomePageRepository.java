package com.example.alldocunemtreader.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.data.model.DocumentCounts;
import com.example.alldocunemtreader.data.model.DocumentInfo;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/24 14:17.
 * Description: com.example.alldocunemtreader.data.repository.HomePageRepository
 */
public class HomePageRepository {
    private final MutableLiveData<DocumentCounts> documentCountsLiveData = new MutableLiveData<>();

    public HomePageRepository() {
        DocumentCounts documentCounts = new DocumentCounts(0, 0, 0, 0, 0, 0, 0);
        documentCountsLiveData.postValue(documentCounts);
    }

    public LiveData<DocumentCounts> getDocumentCountsLiveData() {
        return documentCountsLiveData;
    }

    public void fetchDocumentCounts(List<DocumentInfo> allDocumentList) {
        int allDocumentCount = allDocumentList.size();
        int allPDFCount = 0, allDOCCount = 0, allXLSCount = 0,
                allPPTCount = 0, allTXTCount = 0, allOTHERCount = 0;

        for (DocumentInfo documentInfo : allDocumentList) {
            switch (documentInfo.getType()) {
                case DocumentRelatedConstants.TYPE_PDF:
                    ++allPDFCount;
                    break;
                case DocumentRelatedConstants.TYPE_DOC:
                    ++allDOCCount;
                    break;
                case DocumentRelatedConstants.TYPE_XLS:
                    ++allXLSCount;
                    break;
                case DocumentRelatedConstants.TYPE_PPT:
                    ++allPPTCount;
                    break;
                case DocumentRelatedConstants.TYPE_TXT:
                    ++allTXTCount;
                    break;
                default:
                    ++allOTHERCount;
            }
        }


        DocumentCounts documentCounts = new DocumentCounts(allDocumentCount, allPDFCount, allDOCCount,
                allXLSCount, allPPTCount, allTXTCount, allOTHERCount);
        documentCountsLiveData.postValue(documentCounts);
    }

}
