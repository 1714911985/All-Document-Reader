package com.example.alldocunemtreader.data.repository;

import android.content.Context;
import android.media.MediaScannerConnection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.DocumentUtils;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Author: Eccentric
 * Created on 2024/6/26 10:56.
 * Description: com.example.alldocunemtreader.data.repository.BaseRepository
 */
public class BaseRepository {
    private final Context context;
    private final DocumentInfoDao documentInfoDao;
    private MutableLiveData<Integer> isFavoriteLiveData = new MutableLiveData<>();

    public BaseRepository(Context context) {
        this.context = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    public void switchFavoriteStatus(DocumentInfo currentFile, List<DocumentInfo> allDocumentList) {
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                documentInfoDao.updateIsFavorite(currentFile.getId(), 1 - currentFile.getIsFavorite());
                for (DocumentInfo documentInfo : allDocumentList) {
                    if (Objects.equals(currentFile.getId(), documentInfo.getId())) {
                        documentInfo.setIsFavorite(1 - currentFile.getIsFavorite());
                    }
                }
                isFavoriteLiveData.postValue(1 - currentFile.getIsFavorite());
            }
        });
    }

    public void changeFileName(String newFileName, DocumentInfo currentFile, List<DocumentInfo> allDocumentList) {
        String newFilePath = DocumentUtils.getParentPath(currentFile.getPath()) + newFileName;
        documentInfoDao.updateFileNameAndFilePath(currentFile.getId(), newFileName, newFilePath);

        for (DocumentInfo documentInfo : allDocumentList) {
            if (Objects.equals(currentFile.getId(), documentInfo.getId())) {
                documentInfo.setName(newFileName);
                documentInfo.setPath(newFilePath);
            }
        }
        processingMediaScanner(context, new File(currentFile.getPath()).getParent());
        EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.DATABASE_FILENAME_UPDATE_SUCCESS, null));
    }

    public LiveData<Integer> getIsFavoriteLiveData() {
        return isFavoriteLiveData;
    }


    public void deleteFile(DocumentInfo currentFile, List<DocumentInfo> allDocumentList) {
        documentInfoDao.deleteById(currentFile.getId());
        Iterator<DocumentInfo> iterator = allDocumentList.iterator();
        while (iterator.hasNext()) {
            DocumentInfo next = iterator.next();
            if (Objects.equals(currentFile.getId(), next.getId())) {
                iterator.remove();
            }
        }
        processingMediaScanner(context, new File(currentFile.getPath()).getParent());
        isFavoriteLiveData.postValue(currentFile.getId());
    }

    private void processingMediaScanner(Context context, String... path) {
        if (path != null && path.length > 0) {
            MediaScannerConnection.scanFile(context, path, null, null);
        }
    }

}
