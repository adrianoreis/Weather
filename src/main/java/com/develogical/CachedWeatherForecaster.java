package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adriano on 12-7-17.
 */
public class CachedWeatherForecaster implements WeatherForecaster {

    private static final int MAX_ENTRIES = 3;
    private final WeatherForecaster weatherForecaster;
    private final Map<String, Forecast> cache = new HashMap<>();

    public CachedWeatherForecaster(WeatherForecaster weatherForecaster) {
        this.weatherForecaster = weatherForecaster;
    }

    @Override
    public Forecast weatherForecastFor(Region region, Day day) {
        if (cache.values().size() > MAX_ENTRIES){
            cache.clear();
        }
        if (cache.containsKey(region.name() + day.name())) {
            return cache.get(region.name() + day.name());
        }
        Forecast forecast = weatherForecaster.weatherForecastFor(region, day);
        cache.put(region.name() + day.name(), forecast);
        return forecast;
    }
}
