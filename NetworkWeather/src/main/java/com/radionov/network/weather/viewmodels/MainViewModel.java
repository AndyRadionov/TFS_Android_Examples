package com.radionov.network.weather.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import com.radionov.network.weather.model.entity.Weather;
import com.radionov.network.weather.model.repository.WeatherRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Andrey Radionov
 */
public class MainViewModel extends ViewModel {

    private final WeatherRepository repository;
    private final MutableLiveData<Pair<Weather, List<Weather>>> weatherLiveData;
    private Disposable disposable;

    public MainViewModel(WeatherRepository repository) {
        this.repository = repository;
        weatherLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Pair<Weather, List<Weather>>> getWeatherLiveData() {
        return weatherLiveData;
    }

    public void fetchWeather(String city) {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }

        cancelSubscription();

        disposable = Single.zip(
                repository.fetchCurrentWeather(city),
                repository.fetchForecastWeather(city),
                Pair::create
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((weatherPair, throwable) -> {

                    if (throwable != null || isWeatherResultEmpty(weatherPair) ) {
                        weatherLiveData.setValue(null);
                    } else {
                        weatherLiveData.setValue(weatherPair);
                    }
                });
    }

    @Override
    protected void onCleared() {
        cancelSubscription();
    }

    private void cancelSubscription() {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private boolean isWeatherResultEmpty(Pair<Weather, List<Weather>> weatherPair) {
        return weatherPair == null || weatherPair.first == null || weatherPair.second == null;
    }
}
