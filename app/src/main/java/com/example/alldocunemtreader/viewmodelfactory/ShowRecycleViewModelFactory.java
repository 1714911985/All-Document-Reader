package com.example.alldocunemtreader.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.data.repository.ShowRecycleRepository;
import com.example.alldocunemtreader.ui.classification.tabs.ShowRecycleViewModel;

/**
 * Author: Eccentric
 * Created on 2024/6/25 16:17.
 * Description: com.example.alldocunemtreader.viewmodel.ShowRecycleViewModelFactory
 */
public class ShowRecycleViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public ShowRecycleViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShowRecycleViewModel.class)) {
            return (T) new ShowRecycleViewModel(application, new ShowRecycleRepository(), DocumentInfoRepository.getInstance(application));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}