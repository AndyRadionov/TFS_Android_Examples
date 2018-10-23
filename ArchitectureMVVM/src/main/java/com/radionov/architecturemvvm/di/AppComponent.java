package com.radionov.architecturemvvm.di;

import com.radionov.architecturemvvm.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Radionov
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
