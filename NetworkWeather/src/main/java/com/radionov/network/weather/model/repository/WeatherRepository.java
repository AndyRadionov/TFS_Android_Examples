package com.radionov.network.weather.model.repository;

import java.util.List;

import io.reactivex.Single;
import com.radionov.network.weather.model.datasource.WeatherApi;
import com.radionov.network.weather.model.entity.Weather;

/**
 * @author Andrey Radionov
 */
public class WeatherRepository {

    private final WeatherApi api;

    public WeatherRepository(WeatherApi api) {
        this.api = api;
    }

    public Single<Weather> fetchCurrentWeather(String city) {
        return api.fetchCurrentWeather(city);
    }

    public Single<List<Weather>> fetchForecastWeather(String city) {
        return api.fetchForecastWeather(city);
    }
}
