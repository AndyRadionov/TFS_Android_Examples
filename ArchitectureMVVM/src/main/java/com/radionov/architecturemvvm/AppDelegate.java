package com.radionov.architecturemvvm;

import android.app.Application;
import android.content.Context;

import com.radionov.architecturemvvm.di.AppComponent;
import com.radionov.architecturemvvm.di.DaggerAppComponent;

import timber.log.Timber;

public class AppDelegate extends Application {

    private AppComponent appComponent;

    public static AppDelegate from(Context context) {
        return (AppDelegate) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
