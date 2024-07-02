package com.example.alldocunemtreader.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Author: Eccentric
 * Created on 2024/6/25 16:18.
 * Description: com.example.alldocunemtreader.data.repository.ShowRecycleRepository
 */
public class ShowRecycleRepository {
    private MutableLiveData<List<DocumentInfo>> documentInfoListLiveData = new MutableLiveData<>();

    public void searchDocumentInfoList(String type, List<Integer> message, List<DocumentInfo> allDocumentList) {
        List<DocumentInfo> documentInfoList = selectCorrespondingDocumentInfo(type, message, allDocumentList);
        documentInfoListLiveData.postValue(documentInfoList);

    }

    public LiveData<List<DocumentInfo>> getDocumentInfoListLiveData() {
        return documentInfoListLiveData;
    }

    /**
     * 选择对应的DocumentInfo组装排序并返回
     */
    private List<DocumentInfo> selectCorrespondingDocumentInfo(String type, List<Integer> message, List<DocumentInfo> allDocumentList) {
        List<DocumentInfo> resultList = new ArrayList<>();

        //根据type组装List
        if (Objects.equals(type, DocumentRelatedConstants.TYPE_ALl)) {
            resultList.addAll(allDocumentList);
        } else if (Objects.equals(type, DocumentRelatedConstants.TYPE_RECENT)) {
            for (DocumentInfo documentInfo : allDocumentList) {
                if (documentInfo.getLatestTime() != 0) {
                    resultList.add(documentInfo);
                }
            }
        } else if (Objects.equals(type, DocumentRelatedConstants.TYPE_FAVORITE)) {
            for (DocumentInfo documentInfo : allDocumentList) {
                if (documentInfo.getIsFavorite() == 1) {
                    resultList.add(documentInfo);
                }
            }
        } else {
            for (DocumentInfo documentInfo : allDocumentList) {
                if (Objects.equals(type, documentInfo.getType())) {
                    resultList.add(documentInfo);
                }
            }
        }

        sortByRequest(resultList, message);
        return resultList;
    }

    /**
     * 根据要求排序
     */
    private void sortByRequest(List<DocumentInfo> resultList, List<Integer> message) {
        Integer sortMethodId = message.get(1);
        Integer orderMethodId = message.get(2);
        resultList.sort(new Comparator<DocumentInfo>() {
            @Override
            public int compare(DocumentInfo o1, DocumentInfo o2) {
                if (orderMethodId == R.id.bdrb_asc) {
                    if (sortMethodId == R.id.bdrb_name) {
                        return o1.getName().compareTo(o2.getName());
                    } else if (sortMethodId == R.id.bdrb_date) {
                        return Long.compare(o1.getCreatedTime(), o2.getCreatedTime());
                    } else if (sortMethodId == R.id.bdrb_type) {
                        return o1.getType().compareTo(o2.getType());
                    } else if (sortMethodId == R.id.bdrb_size) {
                        return Long.compare(o1.getSize(), o2.getSize());
                    }
                } else if (orderMethodId == R.id.bdrb_desc) {
                    if (sortMethodId == R.id.bdrb_name) {
                        return o2.getName().compareTo(o1.getName());
                    } else if (sortMethodId == R.id.bdrb_date) {
                        return Long.compare(o2.getCreatedTime(), o1.getCreatedTime());
                    } else if (sortMethodId == R.id.bdrb_type) {
                        return o2.getType().compareTo(o1.getType());
                    } else if (sortMethodId == R.id.bdrb_size) {
                        return Long.compare(o2.getSize(), o1.getSize());
                    }
                }
                return 0;
            }
        });
    }

}
