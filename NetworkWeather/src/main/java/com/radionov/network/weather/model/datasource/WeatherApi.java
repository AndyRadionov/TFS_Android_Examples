package com.radionov.network.weather.model.datasource;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.radionov.network.weather.BuildConfig;
import com.radionov.network.weather.model.entity.Weather;

/**
 * @author Andrey Radionov
 */
public interface WeatherApi {

    @GET(BuildConfig.ApiUrl + "weather")
    Single<Weather> fetchCurrentWeather(@Query("q") String city);

    @GET(BuildConfig.ApiUrl + "forecast")
    Single<List<Weather>> fetchForecastWeather(@Query("q") String city);
}
