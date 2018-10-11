package com.radionov.recyclerview.recycler;

import android.support.v7.util.DiffUtil;

import com.radionov.recyclerview.Worker;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public class WorkersDiffCallback extends DiffUtil.Callback {

    private List<Worker> oldWorkers;
    private List<Worker> newWorkers;

    public WorkersDiffCallback(List<Worker> oldWorkers, List<Worker> newWorkers) {
        this.oldWorkers = oldWorkers;
        this.newWorkers = newWorkers;
    }

    @Override
    public int getOldListSize() {
        return oldWorkers.size();
    }

    @Override
    public int getNewListSize() {
        return newWorkers.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return oldWorkers.get(oldPosition).getId() == newWorkers.get(newPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        return oldWorkers.get(oldPosition).equals(newWorkers.get(newPosition));
    }
}
