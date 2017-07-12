package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

/**
 * Created by adriano on 12-7-17.
 */
public class CachedWeatherForecaster implements WeatherForecaster{

    private final WeatherForecaster weatherForecaster;

    public CachedWeatherForecaster(WeatherForecaster weatherForecaster) {
        this.weatherForecaster = weatherForecaster;
    }

    @Override
    public Forecast weatherForecastFor(Region region, Day day) {
        return weatherForecaster.weatherForecastFor(region, day);
    }
}
