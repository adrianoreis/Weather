package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

/**
 * Created by adriano on 12-7-17.
 */
public interface WeatherForecaster {
    Forecast weatherForecastFor(Region region, Day day);
}
