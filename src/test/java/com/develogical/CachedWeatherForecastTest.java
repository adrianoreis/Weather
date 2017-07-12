package com.develogical;

import com.sun.org.apache.regexp.internal.RE;
import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by adriano on 13-7-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedWeatherForecastTest {
    @InjectMocks
    CachedWeatherForecaster cachedWeatherForecaster;
    @Mock
    WeatherForecaster weatherForecaster;
    @Mock
    private Forecast forecast;

    @Before
    public void setUp() throws Exception {
        when(weatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY)).thenReturn(forecast);
    }

    @Test
    public void testRetrieveForecast() throws Exception {
        Forecast forecastFor = cachedWeatherForecaster.weatherForecastFor(Region.LONDON, Day.FRIDAY);
        assertEquals(forecast, forecastFor);
    }
}
