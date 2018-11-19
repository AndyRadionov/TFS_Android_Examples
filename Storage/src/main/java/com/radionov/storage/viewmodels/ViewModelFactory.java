package com.radionov.storage.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.radionov.storage.data.repositories.NodeRepository;

/**
 * @author Andrey Radionov
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private NodeRepository nodeRepository;

    public ViewModelFactory(NodeRepository repository) {
        nodeRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(nodeRepository);
        } else if (modelClass.isAssignableFrom(AddNodeViewModel.class)) {
            return (T) new AddNodeViewModel(nodeRepository);
        } else if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(nodeRepository);
        } else{
            throw new IllegalArgumentException("ViewModel type was not found");
        }
    }
}
