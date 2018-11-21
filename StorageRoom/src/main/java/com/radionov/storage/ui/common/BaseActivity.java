package com.radionov.storage.ui.common;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.radionov.storage.app.App;

import javax.inject.Inject;

/**
 * @author Andrey Radionov
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.from(this).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }
}
