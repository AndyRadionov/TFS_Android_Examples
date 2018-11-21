package com.radionov.storage.ui.details;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.radionov.storage.R;
import com.radionov.storage.ui.common.BaseActivity;
import com.radionov.storage.ui.common.RelationType;
import com.radionov.storage.viewmodels.DetailsViewModel;

public class DetailsActivity extends BaseActivity {

    public static final String EXTRA_NODE_ID = "node_id";

    private DetailsViewModel detailsViewModel;
    private RelationsAdapter relationsAdapter;
    private long currentNodeId;

    private RelationsAdapter.OnItemClickListener clickListener = new RelationsAdapter.OnItemClickListener() {

        @Override
        public void onAdd(long id) {
            detailsViewModel.addRelation(currentNodeId, id);
        }

        @Override
        public void onRemove(long id) {
            detailsViewModel.removeRelation(currentNodeId, id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        currentNodeId = getIntent().getLongExtra(EXTRA_NODE_ID, 0);

        initViewModel();
        initViews();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_children: {
                    detailsViewModel.setType(RelationType.CHILD);
                    relationsAdapter.setType(RelationType.CHILD);
                    return true;
                }
                case R.id.action_parents: {
                    detailsViewModel.setType(RelationType.PARENT);
                    relationsAdapter.setType(RelationType.PARENT);
                    return true;
                }
                default: return false;
            }
        });
        RelationType type = detailsViewModel.getType();
        if (type == RelationType.PARENT) {
            bottomNavigation.setSelectedItemId(R.id.action_parents);
        } else {
            bottomNavigation.setSelectedItemId(R.id.action_children);
        }
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.container_relations);
        relationsAdapter = new RelationsAdapter(clickListener, currentNodeId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(relationsAdapter);
    }

    private void initViewModel() {
        detailsViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(DetailsViewModel.class);

        detailsViewModel.getNodesLiveData(currentNodeId)
                .observe(this, nodes -> {
                    if (nodes != null && !nodes.isEmpty()) {
                        relationsAdapter.updateData(nodes);
                    }
                });
    }
}
