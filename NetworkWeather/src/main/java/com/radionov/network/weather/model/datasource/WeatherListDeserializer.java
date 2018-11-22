package com.radionov.network.weather.model.datasource;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.radionov.network.weather.model.entity.Weather;

/**
 * @author Andrey Radionov
 */
public class WeatherListDeserializer implements JsonDeserializer<List<Weather>> {
    @Override
    public List<Weather> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Weather> result = new ArrayList<>();

        JsonArray weatherArray = json.getAsJsonObject().get("list").getAsJsonArray();
        for (JsonElement jsonElement : weatherArray) {
            Weather weather = context.deserialize(jsonElement, Weather.class);
            result.add(weather);
        }

        return result;
    }
}
