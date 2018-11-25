package com.radionov.storage.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.radionov.storage.data.entities.NodeWithRelations;
import com.radionov.storage.data.repositories.NodeRepository;
import com.radionov.storage.ui.common.RelationType;
import com.radionov.storage.ui.details.ActionType;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public class DetailsViewModel extends ViewModel {

    private NodeRepository nodeRepository;
    private RelationType currentType = RelationType.CHILD;
    private long currentNodeId;
    private long relationNodeId;
    private ActionType actionType;

    public DetailsViewModel(NodeRepository repository) {
        nodeRepository = repository;
    }

    public void setType(RelationType type) {
        currentType = type;
    }

    public RelationType getType() {
        return currentType;
    }

    public LiveData<List<NodeWithRelations>> getNodesLiveData(long nodeId) {
        return nodeRepository.getAllNodesWithoutRelated(nodeId);
    }

    public void addRelation(long nodeId, long relationId) {
        if (currentType == RelationType.PARENT) {
            nodeRepository.addChild(relationId, nodeId);
        } else {
            nodeRepository.addChild(nodeId, relationId);
        }
    }

    public void removeRelation(long nodeId, long relationId) {
        if (currentType == RelationType.PARENT) {
            nodeRepository.removeChild(relationId, nodeId);
        } else {
            nodeRepository.removeChild(nodeId, relationId);
        }
    }

    public void setAction(long currentNodeId, long id, ActionType actionType) {
        this.currentNodeId = currentNodeId;
        this.relationNodeId = id;
        this.actionType = actionType;
    }

    public void performAction() {
        if (actionType == ActionType.ADD) {
            addRelation(currentNodeId, relationNodeId);
        } else {
            removeRelation(currentNodeId, relationNodeId);
        }
    }
}
