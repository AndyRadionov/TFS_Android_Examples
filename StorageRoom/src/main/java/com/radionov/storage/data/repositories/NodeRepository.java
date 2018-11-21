package com.radionov.storage.data.repositories;

import android.arch.lifecycle.LiveData;

import com.radionov.storage.data.datasource.NodeDao;
import com.radionov.storage.data.entities.Node;
import com.radionov.storage.data.entities.NodeChildren;
import com.radionov.storage.data.entities.NodeWithRelations;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author Andrey Radionov
 */
public class NodeRepository {
    private NodeDao nodeDao;
    private Executor diskIO;

    public NodeRepository(NodeDao nodeDao, Executor diskIO) {
        this.nodeDao = nodeDao;
        this.diskIO = diskIO;
    }

    public LiveData<List<NodeWithRelations>> getAllNodes() {
        return nodeDao.getAllNodes();
    }

    public LiveData<List<NodeWithRelations>> getAllNodesWithoutRelated(long nodeId) {
        return nodeDao.getAllNodesWithoutRelated(nodeId);
    }

    public void addNode(int value) {
        diskIO.execute(() -> nodeDao.addNode(new Node(value)));
    }

    public void addChild(long parentId, long childId) {
        diskIO.execute(() -> nodeDao.addChild(new NodeChildren(parentId, childId)));
    }

    public void removeChild(long parentId, long childId) {
        diskIO.execute(() -> nodeDao.removeChild(new NodeChildren(parentId, childId)));
    }
}
