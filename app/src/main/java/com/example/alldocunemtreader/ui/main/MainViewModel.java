package com.example.alldocunemtreader.ui.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.model.FileItem;
import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.data.repository.ScanRepository;
import com.example.alldocunemtreader.utils.DocumentUtils;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Author: Eccentric
 * Created on 2024/6/21 18:12.
 * Description: com.example.alldocunemtreader.ui.main.MainViewModel
 */
public class MainViewModel extends AndroidViewModel {
    private final ScanRepository scanRepository;
    private final DocumentInfoRepository documentInfoRepository;

    public MainViewModel(@NonNull Application application, ScanRepository scanRepository, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.scanRepository = scanRepository;
        this.documentInfoRepository = documentInfoRepository;
    }

    @SuppressLint("CheckResult")
    public void startScan() {
        Trace documnetTrace = FirebasePerformance.getInstance().newTrace("文档扫描");
        documnetTrace.start();
        Observable.fromCallable(() -> {
                    scanRepository.startScan();
                    return Completable.complete();
                }).subscribeOn(Schedulers.io())
                .subscribe(completable -> {
                    documentInfoRepository.fetchCache();
                    documnetTrace.stop();
                });

        Trace fileTrace = FirebasePerformance.getInstance().newTrace("文件扫描");
        fileTrace.start();
        Single.fromCallable(() -> startScan(Environment.getExternalStorageDirectory(), null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileItemList -> {
                    documentInfoRepository.updateFileItemList(fileItemList);
                    fileTrace.stop();
                });
    }

    private List<FileItem> startScan(File directory, FileItem parent) {
        List<FileItem> fileItems = new ArrayList<>();

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory() && !file.isHidden()) {
                        // 如果是目录，递归扫描子目录并添加到 FileItem 的 children 列表中
                        FileItem directoryItem = new FileItem();
                        directoryItem.setName(file.getName());
                        directoryItem.setType(DocumentRelatedConstants.TYPE_DIRECTORY);
                        directoryItem.setIconId(R.drawable.ic_folder_storage);
                        directoryItem.setParent(parent);
                        List<FileItem> children = startScan(file, directoryItem);
                        directoryItem.setChildren(children);
                        fileItems.add(directoryItem);

                        if (parent != null && parent.getChildren() != null) {
                            parent.getChildren().add(directoryItem);
                        }
                    } else {
                        // 如果是文档类型文件，添加到 FileItem 列表中
                        String fileType = DocumentUtils.getFileType(file.getName());
                        if (!Objects.equals(fileType, DocumentRelatedConstants.TYPE_UNKNOWN) && !file.isHidden()) {
                            FileItem documentItem = new FileItem();
                            documentItem.setName(file.getName());
                            documentItem.setType(fileType);
                            documentItem.setParent(parent);
                            documentItem.setIconId(DocumentUtils.getIconResourceForFileType(fileType));
                            fileItems.add(documentItem);

                            if (parent != null && parent.getChildren() != null) {
                                parent.getChildren().add(documentItem);
                            }
                        }
                    }
                }
            }
        }

        //排序   文件夹在前  文件在后   按首字母排
        fileItems.sort((o1, o2) -> {
            if (Objects.equals(o1.getType(), DocumentRelatedConstants.TYPE_DIRECTORY) && !Objects.equals(o2.getType(), DocumentRelatedConstants.TYPE_DIRECTORY)) {
                return -1;
            } else if (!Objects.equals(o1.getType(), DocumentRelatedConstants.TYPE_DIRECTORY) && Objects.equals(o2.getType(), DocumentRelatedConstants.TYPE_DIRECTORY)) {
                return 1;
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return fileItems;
    }
}
