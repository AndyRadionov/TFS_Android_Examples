package com.radionov.architecturemvvm.di;

import android.arch.lifecycle.ViewModelProvider;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.radionov.architecturemvvm.BuildConfig;
import com.radionov.architecturemvvm.data.remote.Api;
import com.radionov.architecturemvvm.data.repository.CoinsRepository;
import com.radionov.architecturemvvm.viewmodels.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Andrey Radionov
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(CoinsRepository repository) {
        return new ViewModelFactory(repository);
    }

    @Singleton
    @Provides
    CoinsRepository provideCoinRepository(Api api) {
        return new CoinsRepository(api);
    }

    @Singleton
    @Provides
    Api provideRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }
}
