package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adriano on 12-7-17.
 */
public class CachedWeatherForecaster implements WeatherForecaster {

    private static final int MAX_ENTRIES = 3;
    private final WeatherForecaster weatherForecaster;
    private final Map<String, Map<Instant, Forecast>> cache = new HashMap<>();
    private final Clock clock;

    public CachedWeatherForecaster(WeatherForecaster weatherForecaster, Clock clock) {
        this.weatherForecaster = weatherForecaster;
        this.clock = clock;
    }

    @Override
    public Forecast weatherForecastFor(Region region, Day day) {
        Instant now = clock.instant();

        if (cache.values().size() > MAX_ENTRIES){
            cache.clear();
        }

        if (cache.containsKey(region.name() + day.name())) {
            Map<Instant, Forecast> val = cache.get(region.name() + day.name());
            if (val.keySet().iterator().next().isAfter(now)) {
                return val.values().iterator().next();
            }
        }


        Map<Instant, Forecast> newForecast = getForecast(region, day, now);
        cache.put(region.name() + day.name(), newForecast);
        return newForecast.values().iterator().next();
    }

    private Map<Instant,Forecast> getForecast(Region region, Day day, Instant now) {
        Map<Instant, Forecast> newForecast = new HashMap<>();
        Forecast forecast = weatherForecaster.weatherForecastFor(region, day);
        newForecast.put(now.plusSeconds(3600), forecast);
        return  newForecast;
    }
}
