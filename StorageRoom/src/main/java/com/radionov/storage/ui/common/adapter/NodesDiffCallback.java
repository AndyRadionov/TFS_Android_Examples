package com.radionov.storage.ui.common.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.radionov.storage.data.entities.NodeWithRelations;

/**
 * @author Andrey Radionov
 */
public class NodesDiffCallback extends DiffUtil.ItemCallback<NodeWithRelations> {

    @Override
    public boolean areItemsTheSame(@NonNull NodeWithRelations node1, @NonNull NodeWithRelations node2) {
        return node1.getNode().getId() == node2.getNode().getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull NodeWithRelations node1, @NonNull NodeWithRelations node2) {
        return node1.equals(node2);
    }
}
