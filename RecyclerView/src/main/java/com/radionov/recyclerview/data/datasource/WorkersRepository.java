package com.radionov.recyclerview.data.datasource;

import com.radionov.recyclerview.data.entities.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Radionov
 */
public class WorkersRepository {
    private static final WorkersRepository INSTANCE = new WorkersRepository();
    private static List<Worker> prevWorkers = new ArrayList<>();

    private WorkersRepository() {
    }

    public static WorkersRepository getInstance() {
        return INSTANCE;
    }

    public List<Worker> getWorkers(int workersCount) {
        prevWorkers = WorkerGenerator.generateWorkers(workersCount);
        return new ArrayList<>(prevWorkers);
    }

    public List<Worker> getPrevWorkers() {
        return new ArrayList<>(prevWorkers);
    }

    public Worker getWorker() {
        Worker worker = WorkerGenerator.generateWorker();
        prevWorkers.add(worker);
        return worker;
    }
}
