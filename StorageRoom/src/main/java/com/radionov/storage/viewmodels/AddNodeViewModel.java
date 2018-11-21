package com.radionov.storage.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.radionov.storage.data.repositories.NodeRepository;

/**
 * @author Andrey Radionov
 */
public class AddNodeViewModel extends ViewModel {
    private NodeRepository nodeRepository;

    public AddNodeViewModel(NodeRepository repository) {
        nodeRepository = repository;
    }

    public void addNode(int value) {
        nodeRepository.addNode(value);
    }
}
