package com.example.alldocunemtreader.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.data.repository.BaseRepository;
import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.ui.base.BaseViewModel;

/**
 * Author: Eccentric
 * Created on 2024/6/26 10:58.
 * Description: com.example.alldocunemtreader.viewmodel.BaseViewModelFactory
 */
public class BaseViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public BaseViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BaseViewModel.class)) {
            return (T) new BaseViewModel(application, new BaseRepository(application), DocumentInfoRepository.getInstance(application));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
