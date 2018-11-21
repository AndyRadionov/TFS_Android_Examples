package com.radionov.storage.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * @author Andrey Radionov
 */
@Entity(tableName = "node_children",
        primaryKeys = {"parentId", "childId"},
        foreignKeys = {
                @ForeignKey(entity = Node.class,
                        parentColumns = "id",
                        childColumns = "parentId",
                        onDelete = CASCADE),
                @ForeignKey(entity = Node.class,
                        parentColumns = "id",
                        childColumns = "childId",
                        onDelete = CASCADE)},
        indices = {@Index("childId")}
)
public class NodeChildren {

    private long parentId;
    private long childId;

    public NodeChildren(long parentId, long childId) {
        this.parentId = parentId;
        this.childId = childId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getChildId() {
        return childId;
    }

    public void setChildId(long childId) {
        this.childId = childId;
    }
}
