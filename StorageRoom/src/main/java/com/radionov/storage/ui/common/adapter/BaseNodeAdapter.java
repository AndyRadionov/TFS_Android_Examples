package com.radionov.storage.ui.common.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radionov.storage.R;
import com.radionov.storage.data.entities.NodeWithRelations;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public abstract class BaseNodeAdapter extends RecyclerView.Adapter<BaseNodeAdapter.BaseNodesViewHolder> {

    private AsyncListDiffer<NodeWithRelations> differ =
            new AsyncListDiffer<>(this, new NodesDiffCallback());

    @NonNull
    @Override
    public abstract BaseNodesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public void onBindViewHolder(@NonNull BaseNodesViewHolder nodesViewHolder, int i) {
        NodeWithRelations node = differ.getCurrentList().get(i);
        nodesViewHolder.nodeValueView.setText(String.valueOf(node.getNode().getValue()));
        nodesViewHolder.nodeValueView.setBackgroundColor(getColor(node));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    protected void updateData(List<NodeWithRelations> nodes) {
        differ.submitList(nodes);
    }

    protected List<NodeWithRelations> getData() {
        return differ.getCurrentList();
    }

    protected abstract int getColor(NodeWithRelations node);

    protected abstract class BaseNodesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nodeValueView;

        public BaseNodesViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nodeValueView = itemView.findViewById(R.id.tv_node_value);
        }

        @Override
        public abstract void onClick(View v);
    }
}
