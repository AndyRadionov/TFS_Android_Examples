package com.radionov.architecturemvvm.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.radionov.architecturemvvm.R;
import com.radionov.architecturemvvm.data.dto.CoinInfo;
import com.radionov.architecturemvvm.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private static final String EXTRA_INFO = "extra_info";

    public static void start(Activity activity, CoinInfo coinInfo) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(EXTRA_INFO, coinInfo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_details);
        setSupportActionBar(binding.toolbar);

        CoinInfo info = (CoinInfo) getIntent().getSerializableExtra(EXTRA_INFO);
        binding.setCoinInfo(info);
        getSupportActionBar().setTitle(info.getSymbol());
    }
}
