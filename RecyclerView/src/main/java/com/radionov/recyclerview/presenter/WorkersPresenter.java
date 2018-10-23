package com.radionov.recyclerview.presenter;

import android.support.v7.util.DiffUtil;

import com.radionov.recyclerview.data.datasource.WorkersRepository;
import com.radionov.recyclerview.data.entities.Worker;
import com.radionov.recyclerview.ui.WorkersView;
import com.radionov.recyclerview.ui.recycler.WorkersDiffCallback;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Andrey Radionov
 */
public class WorkersPresenter {
    private static final WorkersPresenter INSTANCE = new WorkersPresenter();

    private WorkersView workersView;
    private WorkersRepository repository;
    private Disposable diffDisposable;

    private WorkersPresenter() {
        repository = WorkersRepository.getInstance();
    }

    public static WorkersPresenter getInstance() {
        return INSTANCE;
    }

    public void getNewWorker() {
        Worker worker = repository.getWorker();
        if (workersView != null) {
            workersView.addNewWorker(worker);
        }
    }

    public void requestWorkersData(int dataSize) {
        List<Worker> oldWorkers = repository.getPrevWorkers();
        List<Worker> newWorkers = repository.getWorkers(dataSize);

        if (diffDisposable != null && diffDisposable.isDisposed()) {
            diffDisposable.dispose();
        }

        diffDisposable = Single
                .fromCallable(() -> {
                    WorkersDiffCallback diffCallback = new WorkersDiffCallback(oldWorkers, newWorkers);
                    return DiffUtil.calculateDiff(diffCallback, false);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(diffResult -> {
                    if (workersView != null) {
                        workersView.updateWorkersData(newWorkers, diffResult);
                    }
                });
    }

    public void attachView(WorkersView workersView) {
        this.workersView = workersView;
    }

    public void detachView() {
        this.workersView = null;
    }
}
