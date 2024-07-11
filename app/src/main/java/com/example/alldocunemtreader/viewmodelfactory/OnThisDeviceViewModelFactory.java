package com.example.alldocunemtreader.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.ui.file.OnThisDeviceViewModel;

/**
 * Author: Eccentric
 * Created on 2024/7/11 10:53.
 * Description: com.example.alldocunemtreader.viewmodelfactory.OnThisDeviceViewModelFactory
 */
public class OnThisDeviceViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public OnThisDeviceViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OnThisDeviceViewModel.class)) {
            return (T) new OnThisDeviceViewModel(application, DocumentInfoRepository.getInstance(application));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
