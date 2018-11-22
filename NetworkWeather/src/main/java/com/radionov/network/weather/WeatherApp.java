package com.radionov.network.weather;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import com.radionov.network.weather.di.AppComponent;
import com.radionov.network.weather.di.DaggerAppComponent;

/**
 * @author Andrey Radionov
 */
public class WeatherApp extends Application {

    private AppComponent appComponent;

    public static WeatherApp from(Context context) {
        return (WeatherApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
        Stetho.initializeWithDefaults(this);

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
