package com.example.alldocunemtreader.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.data.repository.SearchRepository;
import com.example.alldocunemtreader.ui.search.SearchViewModel;

/**
 * Author: Eccentric
 * Created on 2024/6/25 14:34.
 * Description: com.example.alldocunemtreader.viewmodel.SearchViewModelFactory
 */
public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public SearchViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(application, new SearchRepository(application));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}