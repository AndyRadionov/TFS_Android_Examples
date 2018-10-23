package com.radionov.architecturemvvm.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.radionov.architecturemvvm.AppDelegate;
import com.radionov.architecturemvvm.R;
import com.radionov.architecturemvvm.data.dto.CoinInfo;
import com.radionov.architecturemvvm.databinding.ActivityMainBinding;
import com.radionov.architecturemvvm.ui.details.DetailsActivity;
import com.radionov.architecturemvvm.viewmodels.MainViewModel;

import java.util.List;

import javax.inject.Inject;

/*
 * TODO:
 * 1) Подключить ViewModel и LiveData из Android Architecture components
 * 2) Разделить классы по пакетам
 * 3) Внедрить в проект архитектуру MVVM, вынести бизнес-логику во вьюмодель.
 * В идеале вьюмодель не должна содержать в себе андроид-компонентов (таких как Context)
 * 4) Сделать так, чтобы при повороте экрана данные не перезапрашивались заново,
 * а использовались полученные ранее
 * 5) Don't repeat yourself - если найдете в коде одинаковые куски кода, выносите в утилитные классы
 */

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    private CoinsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        AppDelegate.from(this).getAppComponent().inject(this);

        initFabListener();
        initRecyclerView();
        initViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            updateData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateData() {
        binding.content.loadingLayout.setVisibility(View.VISIBLE);
        mainViewModel.updateData();
    }

    private void setData(List<CoinInfo> data) {
        setViewsVisibility(View.GONE, View.VISIBLE, View.GONE);
        adapter.setData(data);
    }

    private void showError() {
        setViewsVisibility(View.GONE, View.GONE, View.VISIBLE);
    }

    private void initFabListener() {
        binding.fab.setOnClickListener(view -> updateData());
    }

    private void initRecyclerView() {
        binding.content.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CoinsAdapter(coinInfo -> DetailsActivity.start(MainActivity.this, coinInfo));
        binding.content.mainRecyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainViewModel.class);

        mainViewModel.getCoinsLiveData()
                .observe(this, coinInfos -> {
                    if (coinInfos != null && coinInfos.isEmpty()) {
                        showError();
                    } else {
                        setData(coinInfos);
                    }
                });
    }

    private void setViewsVisibility(int loading, int content, int error) {
        binding.content.loadingLayout.setVisibility(loading);
        binding.content.mainRecyclerView.setVisibility(content);
        binding.content.errorLayout.setVisibility(error);
    }
}
