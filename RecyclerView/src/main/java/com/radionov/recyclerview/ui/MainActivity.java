package com.radionov.recyclerview.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.radionov.recyclerview.R;
import com.radionov.recyclerview.data.entities.Worker;
import com.radionov.recyclerview.presenter.WorkersPresenter;
import com.radionov.recyclerview.ui.recycler.ItemTouchHelperCallback;
import com.radionov.recyclerview.ui.recycler.RecyclerWithEmptyView;
import com.radionov.recyclerview.ui.recycler.SimpleDividerItemDecoration;
import com.radionov.recyclerview.ui.recycler.WorkersAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements WorkersView {

    private static final int TEST_WORKERS_COUNT = 5;

    private WorkersPresenter presenter;
    private RecyclerWithEmptyView workersRecyclerView;
    private WorkersAdapter workersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workersRecyclerView = findViewById(R.id.recycler_view);
        workersRecyclerView.setEmptyView(findViewById(R.id.tv_empty_view));
        workersRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        initPresenter();
        initFab();
        initRecycler();
        presenter.requestWorkersData(TEST_WORKERS_COUNT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void updateWorkersData(List<Worker> newWorkers, DiffUtil.DiffResult diffResult) {
        workersAdapter.updateWorkersList(newWorkers, diffResult);
    }

    @Override
    public void addNewWorker(Worker worker) {
        workersAdapter.addItem(worker);
        workersRecyclerView.smoothScrollToPosition(workersAdapter.getItemCount());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_list) {
            presenter.requestWorkersData(TEST_WORKERS_COUNT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPresenter() {
        presenter = WorkersPresenter.getInstance();
        presenter.attachView(this);
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            /**
             * Реализовать добавление тестовых работников
             */
            presenter.getNewWorker();
        });
    }

    private void initRecycler() {
        /**
         * Реализовать адаптер, выбрать любой LayoutManager и прикрутить это всё к RecyclerView
         *
         * Тестовые данные для отображения генерятся WorkerGenerator
         */
        workersAdapter = new WorkersAdapter();
        workersRecyclerView.setAdapter(workersAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        workersRecyclerView.setLayoutManager(layoutManager);

        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(workersAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(workersRecyclerView);
    }
}
