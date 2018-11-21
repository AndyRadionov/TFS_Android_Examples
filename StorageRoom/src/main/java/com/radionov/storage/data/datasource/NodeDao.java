package com.radionov.storage.data.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.radionov.storage.data.entities.Node;
import com.radionov.storage.data.entities.NodeChildren;
import com.radionov.storage.data.entities.NodeWithRelations;

import java.util.List;

/**
 * @author Andrey Radionov
 */
@Dao
public interface NodeDao {

    @Transaction
    @Query("SELECT * FROM nodes")
    LiveData<List<NodeWithRelations>> getAllNodes();

    @Transaction
    @Query("SELECT * FROM nodes WHERE nodes.id!=:nodeId")
    LiveData<List<NodeWithRelations>> getAllNodesWithoutRelated(long nodeId);

    @Insert
    void addNode(Node node);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addChild(NodeChildren nodeChildren);

    @Delete
    void removeChild(NodeChildren nodeChildren);
}
