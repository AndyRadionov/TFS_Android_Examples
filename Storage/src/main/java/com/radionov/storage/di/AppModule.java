package com.radionov.storage.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import com.radionov.storage.app.AppExecutors;
import com.radionov.storage.data.datasource.NodeRoomDatabase;
import com.radionov.storage.data.repositories.NodeRepository;
import com.radionov.storage.viewmodels.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Andrey Radionov
 */
@Module
public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(NodeRepository repository) {
        return new ViewModelFactory(repository);
    }

    @Singleton
    @Provides
    NodeRepository provideNodeRepository() {
        return new NodeRepository(NodeRoomDatabase.getDatabase(app).nodeDao(),
                AppExecutors.getInstance().diskIO());
    }
}
