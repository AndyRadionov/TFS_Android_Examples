package com.radionov.architecturemvvm.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.radionov.architecturemvvm.data.repository.CoinsRepository;

/**
 * @author Andrey Radionov
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private CoinsRepository coinsRepository;

    public ViewModelFactory(CoinsRepository repository) {
        coinsRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(coinsRepository);
        } else {
            throw new IllegalArgumentException("ViewModel type was not found");
        }
    }
}
