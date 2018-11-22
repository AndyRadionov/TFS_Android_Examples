package com.radionov.network.weather.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import com.radionov.network.weather.R;
import com.radionov.network.weather.WeatherApp;
import com.radionov.network.weather.model.entity.Weather;
import com.radionov.network.weather.utils.NetworkUtils;
import com.radionov.network.weather.viewmodels.MainViewModel;

/**
 * Реализовать приложение, показывающее текущую погоду в городе из предложенного списка.
 * Часть 1. Подготавливаем окружение для взаимодействия с сервером.
 * 1) Сперва получаем ключ для разработчика (Достаточно зарегистрироваться на сайте, он бесплатный) инструкция: https://openweathermap.org/appid
 * <p>
 * 2) Выполнить 2 запроса для получения текущий погоды и прогноза одного из следующих городов:
 * Moscow,RU
 * Sochi,RU
 * Vladivostok,RU
 * Chelyabinsk,RU
 * API запроса By city name можно прочитать тут:
 * https://openweathermap.org/current#name
 * <p>
 * 1) Шаблон запроса на текущую погоду: api.openweathermap.org/data/2.5/weather?q={city name},{country code}
 * Пример: http://api.openweathermap.org/data/2.5/weather?q=Moscow,ru&APPID=7910f4948b3dcb251ebc828f28d8b30b
 * <p>
 * 2) Шаблон запроса на прогноз погоды: api.openweathermap.org/data/2.5/forecast?q={city name},{country code}
 * Пример: http://api.openweathermap.org/data/2.5/forecast?q=Moscow,ru&APPID=7910f4948b3dcb251ebc828f28d8b30b
 * <p>
 * Важно: Данные с сервера должны приходить в json формате (прим.: значение температуры в градусах Цельсия). Также можно добавить локализацию языка: https://openweathermap.org/current#other
 * <p>
 * Часть 2. Разработка мобильного приложения.
 * Шаблон проекта находиться в ветке: homework_9_network
 * UI менять не надо, используем уже реализованные методы MainActivity.
 * Написать код выполнения запроса в методе performRequest(@NonNull String city).
 * <p>
 * Реализовать следующий функционал:
 * a) С помощью Retrofit, Gson и других удобных для вас инструментов, написать запросы для получения текущий и прогнозы погоды в конкретном городе, используя метод API By city name.
 * б) Реализовать JsonDeserializer, который преобразует json структуру пришедшую с сервера в модель Weather (Также и для прогноза погоды). в) Во время загрузки данных показывать прогресс бар, в случае ошибки выводить соотвествующее сообщение.
 * г) Если у пользователя нет доступа в интернет, кнопка выполнить запрос не активна. При его появлении/отсутствии необходимо менять состояние кнопки;
 * д) (Дополнительное задание) Улучшить форматирование вывода данных на свое усмотрение, текущий погоды и прогноза. Оценивается UI интерфейс.
 */

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel mainViewModel;
    private DateFormat dateFormat;

    private Spinner spinner;
    private Button performBtn;
    private ProgressBar progressBar;
    private CardView weatherContainer;
    private TextView todayDate;
    private ImageView todayIcon;
    private TextView todayDescription;
    private TextView todayWind;
    private TextView todayTemp;

    private WeatherAdapter weatherAdapter;

    private final BroadcastReceiver networkStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setEnablePerformButton(NetworkUtils.isInternetAvailable(MainActivity.this));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WeatherApp.from(this).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewModels();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStatusReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStatusReceiver);
    }

    private void initViews() {
        dateFormat = new SimpleDateFormat(getString(R.string.today_format), Locale.ROOT);
        spinner = findViewById(R.id.spinner);
        performBtn = findViewById(R.id.performBtn);
        progressBar = findViewById(R.id.progressBar);
        weatherContainer = findViewById(R.id.weather_container);
        todayDate = findViewById(R.id.date);
        todayIcon = findViewById(R.id.weather_icon);
        todayDescription = findViewById(R.id.weather_description);
        todayWind = findViewById(R.id.wind);
        todayTemp = findViewById(R.id.temperature);
        performBtn.setOnClickListener(v -> performRequest(spinner.getSelectedItem().toString()));

        RecyclerView forecastContainer = findViewById(R.id.forecast_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        weatherAdapter = new WeatherAdapter();
        forecastContainer.setLayoutManager(layoutManager);
        forecastContainer.setAdapter(weatherAdapter);
    }

    private void initViewModels() {
        mainViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainViewModel.class);

        mainViewModel.getWeatherLiveData()
                .observe(this, weatherPair -> {
                    showProgress(false);
                    setEnablePerformButton(true);
                    if (weatherPair == null) {
                        showError(getString(R.string.error_data_not_found));
                    } else {
                        printResult(weatherPair.first, weatherPair.second);
                    }
                });
    }

    private void setEnablePerformButton(boolean enable) {
        performBtn.setEnabled(enable);
    }

    @SuppressLint("DefaultLocale")
    private void printResult(@NonNull Weather weather, @NonNull List<Weather> forecast) {
        weatherContainer.setVisibility(View.VISIBLE);
        todayDate.setText(dateFormat.format(new Date(weather.time)));
        todayDescription.setText(weather.description);
        todayWind.setText(getString(R.string.wind_format, weather.speedWind));
        todayTemp.setText(getString(R.string.temp_format, weather.temp));
        Picasso.get().load(weather.icon).centerInside().fit().into(todayIcon);

        weatherAdapter.updateData(forecast);
    }

    private void showProgress(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void showError(@NonNull String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void performRequest(@NonNull String city) {
        // Здесь необходимо написать свой код.
        // Вызываем нужные методы в нужном порядке.

        showProgress(true);
        setEnablePerformButton(false);
        mainViewModel.fetchWeather(city);
    }
}
