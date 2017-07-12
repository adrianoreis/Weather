package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

/**
 * Created by adriano on 12-7-17.
 */
public class WeatherForecasterAdapter implements  WeatherForecaster {
    private final Forecaster forecaster;

    public WeatherForecasterAdapter(Forecaster forecaster) {
        this.forecaster = forecaster;
    }

    @Override
    public Forecast weatherForecastFor(Region region, Day day) {

        return forecaster.forecastFor(region, day);
    }
}
