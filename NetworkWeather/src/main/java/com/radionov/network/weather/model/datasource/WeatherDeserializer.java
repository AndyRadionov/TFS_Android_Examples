package com.radionov.network.weather.model.datasource;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import com.radionov.network.weather.model.entity.Weather;

/**
 * @author Andrey Radionov
 */
public class WeatherDeserializer implements JsonDeserializer<Weather> {

    private static final String ICON_URL_FORMAT = "http://openweathermap.org/img/w/%s.png";

    @Override
    public Weather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject responseJson = json.getAsJsonObject();

        long time = responseJson.get("dt").getAsLong() * 1000;

        JsonObject weatherJson = responseJson.getAsJsonArray("weather").get(0).getAsJsonObject();
        String description = weatherJson.get("description").getAsString();
        String icon = String.format(ICON_URL_FORMAT, weatherJson.get("icon").getAsString());

        JsonObject mainJson = responseJson.getAsJsonObject("main");
        float temp = mainJson.get("temp").getAsFloat();

        JsonObject windJson = responseJson.getAsJsonObject("wind");
        float speedWind = windJson.get("speed").getAsFloat();

        return new Weather(description, time, temp, speedWind, icon);
    }
}
