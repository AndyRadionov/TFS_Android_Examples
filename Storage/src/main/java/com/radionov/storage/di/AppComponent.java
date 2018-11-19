package com.radionov.storage.di;

import com.radionov.storage.ui.addnode.AddFragmentDialog;
import com.radionov.storage.ui.common.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Radionov
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(BaseActivity baseActivity);
    void inject(AddFragmentDialog mainActivity);
}
