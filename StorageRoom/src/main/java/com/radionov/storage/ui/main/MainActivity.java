package com.radionov.storage.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.radionov.storage.R;
import com.radionov.storage.ui.addnode.AddFragmentDialog;
import com.radionov.storage.ui.common.BaseActivity;
import com.radionov.storage.ui.details.DetailsActivity;
import com.radionov.storage.viewmodels.MainViewModel;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private NodesAdapter nodesAdapter;
    private NodesAdapter.OnItemClickListener clickListener = id -> {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_NODE_ID, id);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewModel();
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.main_nodes_container);
        FloatingActionButton fab = findViewById(R.id.fab_add);
        nodesAdapter = new NodesAdapter(clickListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nodesAdapter);
        fab.setOnClickListener(v -> {
            AddFragmentDialog dialog = new AddFragmentDialog();
            dialog.show(getSupportFragmentManager(), AddFragmentDialog.TAG);
        });
    }

    private void initViewModel() {
        MainViewModel mainViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainViewModel.class);

        mainViewModel.getNodesLiveData()
                .observe(this, nodes -> {
                    if (nodes != null && !nodes.isEmpty()) {
                        nodesAdapter.updateData(nodes);
                    }
                });
    }
}
