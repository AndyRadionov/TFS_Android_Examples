package com.radionov.storage.ui.details;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radionov.storage.R;
import com.radionov.storage.data.entities.NodeWithRelations;
import com.radionov.storage.ui.common.RelationType;
import com.radionov.storage.ui.common.adapter.BaseNodeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Andrey Radionov
 */
public class RelationsAdapter extends BaseNodeAdapter {

    private OnItemClickListener clickListener;
    private NodesComparator comparator;
    private long currentNodeId;
    private RelationType currentType;

    public interface OnItemClickListener {
        void onAdd(long id);
        void onRemove(long id);
    }

    public RelationsAdapter(OnItemClickListener clickListener, long nodeId) {
        this.clickListener = clickListener;
        currentNodeId = nodeId;
        currentType = RelationType.CHILD;
        comparator = new NodesComparator();
    }

    @NonNull
    @Override
    public RelationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_node, parent, false);

        return new RelationsViewHolder(itemView);
    }

    @Override
    public void updateData(List<NodeWithRelations> nodes) {
        sortList(nodes);
        super.updateData(nodes);
    }

    public void setType(RelationType type) {
        currentType = type;
        List<NodeWithRelations> nodes = new ArrayList<>(getData());
        updateData(nodes);
        notifyDataSetChanged();
    }

    public void sortList(List<NodeWithRelations> nodes) {
        Collections.sort(nodes, comparator);
    }

    @Override
    protected int getColor(NodeWithRelations node) {
        if (isParent(node) || isChild(node)) {
            return Color.GREEN;
        } else {
            return Color.TRANSPARENT;
        }
    }

    private boolean isChild(NodeWithRelations node) {
        return currentType == RelationType.CHILD && node.getParentIds().contains(currentNodeId);
    }

    private boolean isParent(NodeWithRelations node) {
        return currentType == RelationType.PARENT && node.getChildrenIds().contains(currentNodeId);
    }

    class RelationsViewHolder extends BaseNodesViewHolder {

        RelationsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            NodeWithRelations node = getData().get(getAdapterPosition());
            long id = node.getNode().getId();
            if (isChild(node) || isParent(node)) {
                clickListener.onRemove(id);
            } else {
                clickListener.onAdd(id);
            }
        }
    }

    private class NodesComparator implements Comparator<NodeWithRelations> {
        @Override
        public int compare(NodeWithRelations o1, NodeWithRelations o2) {
            if (isChild(o1) && !isChild(o2) || isParent(o1) && !isParent(o2)) {
                return -1;
            } else {
                return (int) (o1.getNode().getId() - o2.getNode().getId());
            }
        }
    }
}