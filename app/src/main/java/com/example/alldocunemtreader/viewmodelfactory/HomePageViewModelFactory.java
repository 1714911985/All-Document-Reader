package com.example.alldocunemtreader.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.data.repository.HomePageRepository;
import com.example.alldocunemtreader.ui.homepage.HomePageViewModel;

/**
 * Author: Eccentric
 * Created on 2024/6/24 14:15.
 * Description: com.example.alldocunemtreader.viewmodel.HomePageViewModelFactory
 */
public class HomePageViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public HomePageViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomePageViewModel.class)) {
            return (T) new HomePageViewModel(application, new HomePageRepository(), DocumentInfoRepository.getInstance(application));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
