package com.radionov.storage.data.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;
import java.util.Objects;

/**
 * @author Andrey Radionov
 */
public class NodeWithRelations {
    @Embedded
    private Node node;

    @Relation(
            parentColumn = "id",
            entityColumn = "parentId",
            entity = NodeChildren.class,
            projection = "childId")
    private List<Long> childrenIds;

    @Relation(
            parentColumn = "id",
            entityColumn = "childId",
            entity = NodeChildren.class,
            projection = "parentId")
    private List<Long> parentIds;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<Long> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<Long> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeWithRelations that = (NodeWithRelations) o;
        return Objects.equals(node, that.node) &&
                Objects.equals(childrenIds, that.childrenIds) &&
                Objects.equals(parentIds, that.parentIds);
    }

    @Override
    public int hashCode() {

        return Objects.hash(node, childrenIds, parentIds);
    }
}
