package com.radionov.network.weather.model.entity;


import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public class Weather {
    public String description;
    public long time;
    public float temp;
    public float speedWind;
    public String icon;

    public Weather(@NonNull String description, long time, float temp, float speedWind, String icon) {
        this.description = description;
        this.time = time;
        this.temp = temp;
        this.speedWind = speedWind;
        this.icon = icon;
    }

    public Weather() {
        //stub
    }

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", temp=" + temp +
                ", speedWind=" + speedWind +
                '}';
    }
}
