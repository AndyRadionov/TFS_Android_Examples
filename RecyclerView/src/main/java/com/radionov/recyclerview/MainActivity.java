package com.radionov.recyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.radionov.recyclerview.recycler.ItemTouchHelperCallback;
import com.radionov.recyclerview.recycler.RecyclerWithEmptyView;
import com.radionov.recyclerview.recycler.SimpleDividerItemDecoration;
import com.radionov.recyclerview.recycler.WorkersAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int TEST_WORKERS_COUNT = 5;

    private RecyclerWithEmptyView workersRecyclerView;
    private WorkersAdapter workersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workersRecyclerView = findViewById(R.id.recycler_view);
        workersRecyclerView.setEmptyView(findViewById(R.id.tv_empty_view));
        workersRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            /**
             * Реализовать добавление тестовых работников
             */
            Worker worker = WorkerGenerator.generateWorker();
            workersAdapter.addItem(worker);
            workersRecyclerView.smoothScrollToPosition(workersAdapter.getItemCount());
        });


        /**
         * Реализовать адаптер, выбрать любой LayoutManager и прикрутить это всё к RecyclerView
         *
         * Тестовые данные для отображения генерятся WorkerGenerator
         */
        List<Worker> testWorkers = WorkerGenerator.generateWorkers(TEST_WORKERS_COUNT);
        workersAdapter = new WorkersAdapter(testWorkers);
        workersRecyclerView.setAdapter(workersAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        workersRecyclerView.setLayoutManager(layoutManager);

        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(workersAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(workersRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_list) {
            workersAdapter.updateWorkersList(WorkerGenerator.generateWorkers(TEST_WORKERS_COUNT));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
