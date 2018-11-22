package com.radionov.network.weather.ui;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.radionov.network.weather.R;
import com.radionov.network.weather.model.entity.Weather;

/**
 * @author Andrey Radionov
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final List<Weather> weathers;
    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.ROOT);
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ROOT);

    public WeatherAdapter() {
        this.weathers = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_forecast, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        final Weather weather = weathers.get(position);
        Resources resources = holder.itemView.getResources();

        Date date = new Date(weather.time);
        Picasso.get().load(weather.icon).centerInside().fit().into(holder.icon);
        holder.date.setText(dateFormat.format(date));
        holder.time.setText(timeFormat.format(date));
        holder.temperature.setText(resources.getString(R.string.temp_format, weather.temp));
        holder.wind.setText(resources.getString(R.string.wind_format, weather.speedWind));
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public void updateData(List<Weather> newData) {
        weathers.clear();
        weathers.addAll(newData);
        notifyDataSetChanged();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView date;
        private final TextView time;
        private final TextView temperature;
        private final TextView wind;

        private WeatherViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.weather_icon);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            temperature = itemView.findViewById(R.id.temperature);
            wind = itemView.findViewById(R.id.wind);
        }
    }
}
