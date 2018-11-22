package com.radionov.network.weather.di;

import android.arch.lifecycle.ViewModelProvider;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.radionov.network.weather.BuildConfig;
import com.radionov.network.weather.model.datasource.WeatherApi;
import com.radionov.network.weather.model.datasource.WeatherDeserializer;
import com.radionov.network.weather.model.datasource.WeatherListDeserializer;
import com.radionov.network.weather.model.entity.Weather;
import com.radionov.network.weather.model.repository.WeatherRepository;
import com.radionov.network.weather.viewmodels.ViewModelFactory;

/**
 * @author Andrey Radionov
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(WeatherRepository repository) {
        return new ViewModelFactory(repository);
    }

    @Singleton
    @Provides
    WeatherRepository provideWeatherRepository(WeatherApi weatherApi) {
        return new WeatherRepository(weatherApi);
    }

    @Singleton
    @Provides
    WeatherApi provideWeatherApi() {
        //Логирование
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor queryInterceptor = chain -> {

            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("APPID", BuildConfig.ApiKey)
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("lang", Locale.getDefault().getLanguage())
                    .build();

            Request request = original.newBuilder().url(url).build();
            return chain.proceed(request);
        };

        //OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(queryInterceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        //Gson парсер
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Weather> deserializer = new WeatherDeserializer();
        gsonBuilder.registerTypeAdapter(Weather.class, deserializer);
        Type listType = new TypeToken<List<Weather>>(){}.getType();
        gsonBuilder.registerTypeAdapter(listType, new WeatherListDeserializer());
        Gson gson = gsonBuilder.create();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.ApiUrl)
                .build();

        //Api weather
        return retrofit.create(WeatherApi.class);
    }
}
