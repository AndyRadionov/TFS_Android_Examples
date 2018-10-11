package com.radionov.recyclerview.recycler;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.radionov.recyclerview.R;
import com.radionov.recyclerview.Worker;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Andrey Radionov
 */
public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.WorkerViewHolder> {

    private final List<Worker> workers;
    private Disposable diffDisposable;

    public WorkersAdapter(@NonNull final List<Worker> workers) {
        this.workers = workers;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_worker, parent, false);
        return new WorkerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        final Worker worker = workers.get(position);
        Resources resources = holder.itemView.getResources();

        holder.photo.setImageResource(worker.getPhoto());
        holder.name.setText(worker.getName());
        holder.position.setText(resources.getString(R.string.position_label_text, worker.getPosition()));
        holder.age.setText(resources.getString(R.string.age_label_text, worker.getAge()));
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    public void onItemMove(int fromPosition, int toPosition) {
        workers.add(toPosition, workers.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onItemDismiss(int position) {
        workers.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Worker worker) {
        workers.add(worker);
        notifyItemInserted(workers.size() - 1);
    }

    public void updateWorkersList(List<Worker> newWorkers) {
        if (diffDisposable != null && diffDisposable.isDisposed()) {
            diffDisposable.dispose();
        }

        diffDisposable = Single
                .fromCallable(() -> {
                    WorkersDiffCallback diffCallback = new WorkersDiffCallback(workers, newWorkers);
                    return DiffUtil.calculateDiff(diffCallback, false);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(diffResult -> {
                    workers.clear();
                    workers.addAll(newWorkers);
                    diffResult.dispatchUpdatesTo(this);
                });
    }

    static class WorkerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView photo;
        private final TextView name;
        private final TextView position;
        private final TextView age;

        private WorkerViewHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.iv_worker_photo);
            name = itemView.findViewById(R.id.tv_worker_name);
            position = itemView.findViewById(R.id.tv_worker_position);
            age = itemView.findViewById(R.id.tv_worker_age);
        }
    }
}
