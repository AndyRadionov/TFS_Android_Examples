package com.radionov.architecturemvvm.ui.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.radionov.architecturemvvm.R;
import com.radionov.architecturemvvm.data.dto.CoinInfo;
import com.radionov.architecturemvvm.databinding.ItemCoinBinding;


import java.util.ArrayList;
import java.util.List;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder> {

    private List<CoinInfo> items = new ArrayList<>();
    private OnItemClickListener listener;

    public CoinsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoinsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCoinBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.item_coin, parent, false);

        return new CoinsViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<CoinInfo> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    static class CoinsViewHolder extends RecyclerView.ViewHolder {

        private ItemCoinBinding binding;
        private OnItemClickListener listener;

        private CoinsViewHolder(ItemCoinBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        void bind(CoinInfo info) {
            binding.setCoinInfo(info);
            itemView.setOnClickListener(view -> listener.onClick(info));
        }
    }

    interface OnItemClickListener {
        void onClick(CoinInfo coinInfo);
    }
}
