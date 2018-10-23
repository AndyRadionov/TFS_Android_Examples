package com.radionov.recyclerview.ui;

import android.support.v7.util.DiffUtil;

import com.radionov.recyclerview.data.entities.Worker;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public interface WorkersView {

    void updateWorkersData(List<Worker> newWorkers, DiffUtil.DiffResult diffResult);

    void addNewWorker(Worker worker);
}
