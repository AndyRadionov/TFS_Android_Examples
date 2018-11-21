package com.radionov.storage.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

/**
 * @author Andrey Radionov
 */
@Entity(tableName = "nodes")
public class Node {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int value;

    public Node(long id, int value) {
        this.id = id;
        this.value = value;
    }

    @Ignore
    public Node(int value) {
        this(0, value);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id && value == node.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, value);
    }
}
