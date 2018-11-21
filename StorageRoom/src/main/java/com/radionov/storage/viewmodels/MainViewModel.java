package com.radionov.storage.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.radionov.storage.data.entities.NodeWithRelations;
import com.radionov.storage.data.repositories.NodeRepository;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public class MainViewModel extends ViewModel {

    private NodeRepository nodeRepository;

    public MainViewModel(NodeRepository repository) {
        nodeRepository = repository;
    }

    public LiveData<List<NodeWithRelations>> getNodesLiveData() {
        return nodeRepository.getAllNodes();
    }
}
