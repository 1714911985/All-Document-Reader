package com.example.alldocunemtreader.ui.base;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.repository.BaseRepository;
import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.example.alldocunemtreader.utils.DocumentUtils;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

import java.io.File;
import java.util.Objects;

/**
 * Author: Eccentric
 * Created on 2024/6/26 10:56.
 * Description: com.example.alldocunemtreader.ui.base.BaseViewModel
 */
public class BaseViewModel extends AndroidViewModel {
    private final BaseRepository baseRepository;
    private final DocumentInfoRepository documentInfoRepository;
    private final Application application;

    public BaseViewModel(@NonNull Application application, BaseRepository baseRepository, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.application = application;
        this.baseRepository = baseRepository;
        this.documentInfoRepository = documentInfoRepository;
    }

    public void switchFavoriteStatus(DocumentInfo currentFile) {
        baseRepository.switchFavoriteStatus(currentFile, documentInfoRepository.getCurrentCache());
    }

    public LiveData<Integer> getIsFavoriteLiveData() {
        return baseRepository.getIsFavoriteLiveData();
    }

    public LiveData<Boolean> getDeleteStateLiveData() {
        return baseRepository.getDeleteStateLiveData();
    }

    public void deleteFile(DocumentInfo currentFile) {
        int index = currentFile.getPath().lastIndexOf("/");
        String parent = currentFile.getPath().substring(0, index);
        File dir = new File(parent);
        if (!dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (Objects.equals(file.getName(), currentFile.getName())) {
                ThreadPoolManager.getInstance().executeSingle(new Runnable() {
                    @Override
                    public void run() {
                        if (file.delete()) {
                            baseRepository.deleteFile(currentFile, documentInfoRepository.getCurrentCache());
                        } else {
                            Toast.makeText(application, application.getText(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }

    /**
     * 刷新RecucleView
     */
    public void refresh() {
        //刷新
        EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_REFRESH,
                ArrangementHelper.getViewSettings()));
    }

    public void changeFileName(DocumentInfo currentFile, String newFileNameWithoutExtension) {
        String fileExtension = DocumentUtils.getFileExtension(currentFile.getName());
        if (!TextUtils.isEmpty(newFileNameWithoutExtension)) {
            File oldFile = new File(currentFile.getPath());
            File newFile = new File(oldFile.getParent() + File.separator + newFileNameWithoutExtension + fileExtension);
            if (oldFile.exists()) {
                if (oldFile.renameTo(newFile)) {
                    final String newFileName = newFileNameWithoutExtension + fileExtension;
                    baseRepository.changeFileName(newFileName, currentFile, documentInfoRepository.getCurrentCache());
                } else {
                    EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.MEDIASTORE_FILENAME_UPDATE_FAILED, null));
                }
            }
        } else {
            EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.NEW_FILE_NAME_CAN_NOT_NULL, null));
        }
    }
}
