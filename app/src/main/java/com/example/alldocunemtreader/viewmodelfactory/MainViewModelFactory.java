package com.example.alldocunemtreader.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.data.repository.ScanRepository;
import com.example.alldocunemtreader.ui.main.MainViewModel;

/**
 * Author: Eccentric
 * Created on 2024/6/24 10:59.
 * Description: com.example.alldocunemtreader.viewmodel.MainViewModelFactory
 */
public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public MainViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(application, new ScanRepository(application));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
