package com.radionov.storage.app;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.radionov.storage.di.AppComponent;
import com.radionov.storage.di.AppModule;
import com.radionov.storage.di.DaggerAppComponent;

/**
 * @author Andrey Radionov
 */
public class App extends Application {

    private AppComponent appComponent;

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        Stetho.initializeWithDefaults(this);

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
