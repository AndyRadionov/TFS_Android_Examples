package com.radionov.storage.ui.main;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radionov.storage.R;
import com.radionov.storage.data.entities.NodeWithRelations;
import com.radionov.storage.ui.common.adapter.BaseNodeAdapter;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public class NodesAdapter extends BaseNodeAdapter {

    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onClick(long id);
    }

    public NodesAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_node, parent, false);

        return new NodesViewHolder(itemView);
    }

    @Override
    public void updateData(List<NodeWithRelations> nodes) {
        super.updateData(nodes);
    }

    @Override
    protected int getColor(NodeWithRelations node) {
        if (hasChildren(node) && hasParents(node)) {
            return Color.RED;
        } else if (hasChildren(node)) {
            return Color.YELLOW;
        } else if (hasParents(node)) {
            return Color.BLUE;
        } else {
            return Color.TRANSPARENT;
        }
    }

    private boolean hasChildren(NodeWithRelations node) {
        return node.getChildrenIds() != null && !node.getChildrenIds().isEmpty();
    }

    private boolean hasParents(NodeWithRelations node) {
        return node.getParentIds() != null && !node.getParentIds().isEmpty();
    }

    class NodesViewHolder extends BaseNodesViewHolder {

        NodesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            long id = getData().get(getAdapterPosition()).getNode().getId();
            clickListener.onClick(id);
        }
    }
}
