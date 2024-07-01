package com.example.alldocunemtreader.data.repository;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.provider.MediaStore;

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

    public void switchFavoriteStatus(DocumentInfo currentFile) {
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                documentInfoDao.updateIsFavorite(currentFile.getId(), 1 - currentFile.getIsFavorite());
                isFavoriteLiveData.postValue(1 - currentFile.getIsFavorite());
            }
        });
    }

    public void changeFileName(String newFileName, DocumentInfo currentFile) {
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, newFileName);
                int update = contentResolver.update(DocumentUtils.changePathToUri(context, currentFile.getPath()),
                        contentValues,
                        null,
                        null);
                if (update > 0) {
                    documentInfoDao.updateFileName(currentFile.getId(), newFileName);
                    EventBusUtils.post(
                            new EventBusMessage<>(RequestCodeConstants.DATABASE_FILENAME_UPDATE_SUCCESS,
                                    null));
                } else {
                    //修改文件名失败
                    EventBusUtils.post(
                            new EventBusMessage<>(RequestCodeConstants.MEDIASTORE_FILENAME_UPDATE_FAILED,
                                    null));
                }
            }
        });
    }

    public LiveData<Integer> getIsFavoriteLiveData() {
        return isFavoriteLiveData;
    }


    public void deleteFile(int id) {
        documentInfoDao.deleteById(id);
        isFavoriteLiveData.postValue(id);
    }

}
