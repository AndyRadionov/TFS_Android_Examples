package com.radionov.network.weather.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.radionov.network.weather.model.repository.WeatherRepository;

/**
 * @author Andrey Radionov
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final WeatherRepository repository;

    public ViewModelFactory(WeatherRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        } else {
            throw new IllegalArgumentException("ViewModel type was not found");
        }
    }
}
